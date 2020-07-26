import {from, Observable, merge, throwError, of} from 'rxjs'
import {catchError, map, tap} from 'rxjs/operators'
import {GraphQlQueryResponse} from '@octokit/graphql/dist-types/types'
import {graphql} from './graphql'

export interface DeletePackageVersionMutationResponse {
  deletePackageVersion: {
    success: boolean
  }
}

const mutation = `
  mutation deletePackageVersion($packageVersionId: String!) {
      deletePackageVersion(input: {packageVersionId: $packageVersionId}) {
          success
      }
  }`

export function deletePackageVersion(
  packageVersionId: string,
  token: string
): Observable<boolean> {
  return from(
    graphql(token, mutation, {
      packageVersionId,
      headers: {
        Accept: 'application/vnd.github.package-deletes-preview+json'
      }
    }) as Promise<DeletePackageVersionMutationResponse>
  ).pipe(
    catchError((err: GraphQlQueryResponse) => {
      const msg = 'delete version mutation failed.'
      return throwError(
        err.errors && err.errors.length > 0
          ? `${msg} ${err.errors[0].message}`
          : `${msg} verify input parameters are correct`
      )
    }),
    map(response => response.deletePackageVersion.success)
  )
}

export function deletePackageVersions(
  packageVersionIds: string[],
  token: string
): Observable<boolean> {
  if (packageVersionIds.length === 0) {
    console.log('no package version ids found, no versions will be deleted')
    return of(true)
  }

  const deletes = packageVersionIds.map(id =>
    deletePackageVersion(id, token).pipe(
      tap(result => {
        if (result) {
          console.log(`version with id: ${id}, deleted`)
        } else {
          console.log(`version with id: ${id}, not deleted`)
        }
      })
    )
  )

  return merge(...deletes)
}
