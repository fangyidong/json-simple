import {getInput, setFailed} from '@actions/core'
import {context} from '@actions/github'
import {Input} from './input'
import {Observable, throwError} from 'rxjs'
import {deleteVersions} from './delete'
import {catchError} from 'rxjs/operators'

function getActionInput(): Input {
  return new Input({
    packageVersionIds: getInput('package-version-ids')
      ? getInput('package-version-ids').split(',')
      : [],
    owner: getInput('owner') ? getInput('owner') : context.repo.owner,
    repo: getInput('repo') ? getInput('repo') : context.repo.repo,
    packageName: getInput('package-name'),
    numOldVersionsToDelete: Number(getInput('num-old-versions-to-delete')),
    token: getInput('token')
  })
}

function run(): Observable<boolean> {
  try {
    return deleteVersions(getActionInput()).pipe(
      catchError(err => throwError(err))
    )
  } catch (error) {
    return throwError(error.message)
  }
}

run().subscribe({
  error: err => {
    setFailed(err)
  }
})
