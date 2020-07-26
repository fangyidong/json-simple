import {GitHub} from '@actions/github'
import {GraphQlQueryResponseData} from '@octokit/graphql/dist-types/types'
import {RequestParameters} from '@octokit/types/dist-types/RequestParameters'

/**
 * Sends a GraphQL query request based on endpoint options
 *
 * @param {string} token Auth token
 * @param {string} query GraphQL query. Example: `'query { viewer { login } }'`.
 * @param {object} parameters URL, query or body parameters, as well as `headers`, `mediaType.{format|previews}`, `request`, or `baseUrl`.
 */
export async function graphql(
  token: string,
  query: string,
  parameters: RequestParameters
): Promise<GraphQlQueryResponseData> {
  const github = new GitHub(token)
  return await github.graphql(query, parameters)
}
