# Release builds

This fork publishes launcher-compatible XMage builds through GitHub Releases.

## Channels

- `beta`: updated automatically on every push to `main`. This is a prerelease and can move from commit to commit.
- `stable`: updated manually from `main` through the `Release builds` workflow.
- `test`: created manually from the `Release builds` workflow on any branch. This only uploads workflow artifacts and does not publish a GitHub Release.

## Launcher URLs

Use these as the XMage Launcher update URLs:

- Beta: `https://github.com/whaitukay/mage/releases/download/beta/`
- Stable: `https://github.com/whaitukay/mage/releases/download/stable/`

Each URL exposes a launcher-readable `config.json`:

- `https://github.com/whaitukay/mage/releases/download/beta/config.json`
- `https://github.com/whaitukay/mage/releases/download/stable/config.json`

## Assets

Each release contains:

- `xmage-<channel>.zip`
- `config.json`
- `SHA256SUMS.txt`
- `build.json`
- `RELEASE_NOTES.md`

The zip file is assembled from the upstream Maven assembly descriptors in `Mage.Client` and `Mage.Server`.

`config.json` exposes a launcher version with both a UTC build timestamp and the commit SHA, for example:

- `1.4.58-dev (beta-2026-05-16-1432Z-697d4fdbd4cd)`
- `1.4.58 (stable-2026-05-16-1432Z-697d4fdbd4cd)`

## Publishing

Pushes to `main` publish the `beta` prerelease automatically.

Pull requests to `main` build the `test` channel automatically and upload workflow artifacts without publishing a GitHub Release.

To test changes from a feature branch after this workflow exists on `main`:

1. Open the repository's Actions tab.
2. Select `Release builds`.
3. Run the workflow manually from the feature branch.
4. Choose `test`.
5. Download the workflow artifact from the completed run.

To publish a stable release:

1. Open the repository's Actions tab.
2. Select `Release builds`.
3. Run the workflow manually from `main`.
4. Choose `stable`.

The stable release updates the fixed `stable` tag and release assets.

## Upstream credit

This repository is a fork of XMage. The project depends on the work of the upstream maintainers and contributors; this release flow only packages this fork's builds.
