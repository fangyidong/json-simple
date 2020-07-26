import {GraphQlQueryResponse} from '@octokit/graphql/dist-types/types'
import {Observable, from, throwError} from 'rxjs'
import {catchError, map} from 'rxjs/operators'
import {graphql} from './graphql'

export interface VersionInfo {
  id: string
  version: string
}

export interface GetVersionsQueryResponse {
  repository: {
    packages: {
      edges: {
        node: {
          name: string
          versions: {
            edges: {node: VersionInfo}[]
          }
        }
      }[]
    }
  }
}

const query = `
  query getVersions($owner: String!, $repo: String!, $package: String!, $last: Int!) {
    repository(owner: $owner, name: $repo) {
      packages(first: 1, names: [$package]) {
        edges {
          node {
            name
            versions(last: $last) {
              edges {
                node {
                  id
                  version
                }
              }
            }
          }
        }
      }
    }
  }`

export function queryForOldestVersions(
  owner: string,
  repo: string,
  packageName: string,
  numVersions: number,
  token: string
): Observable<GetVersionsQueryResponse> {
  return from(
    graphql(token, query, {
      owner,
      repo,
      package: packageName,
      last: numVersions,
      headers: {
        Accept: 'application/vnd.github.packages-preview+json'
      }
    }) as Promise<GetVersionsQueryResponse>
  ).pipe(
    catchError((err: GraphQlQueryResponse) => {
      const msg = 'query for oldest version failed.'
      return throwError(
        err.errors && err.errors.length > 0
          ? `${msg} ${err.errors[0].message}`
          : `${msg} verify input parameters are correct`
      )
    })
  )
}

export function getOldestVersions(
  owner: string,
  repo: string,
  packageName: string,
  numVersions: number,
  token: string
): Observable<VersionInfo[]> {
  return queryForOldestVersions(
    owner,
    repo,
    packageName,
    numVersions,
    token
  ).pipe(
    map(result => {
      if (result.repository.packages.edges.length < 1) {
        throwError(
          `package: ${packageName} not found for owner: ${owner} in repo: ${repo}`
        )
      }

      const versions = result.repository.packages.edges[0].node.versions.edges

      if (versions.length !== numVersions) {
        console.log(
          `number of versions requested was: ${numVersions}, but found: ${versions.length}`
        )
      }

      return versions
        .map(value => ({id: value.node.id, version: value.node.version}))
        .reverse()
    })
  )
}
