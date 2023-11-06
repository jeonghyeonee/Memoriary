# Memoriary

2023 3-2 Mobile Programming Team Project

## Team Member

|                        Kim Siheon                         |                        Park Hyunseo                        |                      Lee Jeonghyeon                       |
| :-------------------------------------------------------: | :--------------------------------------------------------: | :-------------------------------------------------------: |
|        [Siheon Kim](https://github.com/siheon0411)        |   [Hyunseo Park (Sean)](https://github.com/hyunwestpark)   |      [jeonghyeonee](https://github.com/jeonghyeonee)      |
| ![](https://avatars.githubusercontent.com/u/68041137?v=4) | ![](https://avatars.githubusercontent.com/u/123967536?v=4) | ![](https://avatars.githubusercontent.com/u/33801356?v=4) |

## How to Collaborate

### 1. Fork

- Fork this repository to your own account.

### 2. Clone

- Clone the repository from your account to your local machine.
  ```sh
  git clone <forked repository address>
  ```

### 3. Add the Upstream Repository

- Add the original repository where the project was initially created as an upstream source.
  ```sh
  git remote add upstream <original repo address>
  git remote add upstream <https://github.com/jeonghyeonee/Memoriary.git>
  ```

### 4. Verify Remote Repositories

- Check the list of remote repositories to ensure that your repository is set as 'origin' and the project's repository is set as 'upstream.'
  ```sh
  git remote -v
  ```

### 5. Create a Branch

- Work on your changes in a new branch on your local machine.
- Follow the branch naming convention: `ID/Name/IssueNumber`.
  ```sh
  # Example: jeonghyeonee/updateReadme/8
  ```

### 6. Add & Commit

- Add your changes using the following command:
  ```sh
  git add . or <specific file>
  ```
- Commit your changes with a meaningful commit message using either `-m` or `-s` (signed-off) option.
  - If you use `-s`, please provide a summary of your commit message.

### 7. Push

- Push your changes to your repository on the branch you created.
  ```sh
  git push origin <branchName>
  ```

### 8. Create a Pull Request

- Go to your repository on GitHub and click the "Compare & Pull Request" button.
- Assign a reviewer if necessary.

### 9. Code Review & Merge Pull Request

- The reviewer will perform a code review and decide whether to merge the pull request if there are no issues.
