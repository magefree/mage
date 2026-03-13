# XMage Ollama Bridge

This fork adds a new XMage AI player type:

- `Computer - ollama`

## What it does

- Uses the local bridge at `http://127.0.0.1:8787` by default
- Calls the bridge for:
  - mulligan decisions
  - top-level priority decisions in `PRECOMBAT_MAIN` and `POSTCOMBAT_MAIN`
- Falls back to XMage's existing mad bot logic if the bridge fails
- Keeps XMage's built-in logic for target selection, mode choice, combat micro-decisions, and other sub-choices

## Configure

Set either system properties or environment variables before starting the XMage server:

- `XMAGE_OLLAMA_BRIDGE_URL`
- `XMAGE_OLLAMA_CONNECT_TIMEOUT_MS`
- `XMAGE_OLLAMA_READ_TIMEOUT_MS`
- `XMAGE_OLLAMA_STRATEGY`

Example:

```bash
export XMAGE_OLLAMA_BRIDGE_URL=http://127.0.0.1:8787
export XMAGE_OLLAMA_CONNECT_TIMEOUT_MS=1500
export XMAGE_OLLAMA_READ_TIMEOUT_MS=2500
export XMAGE_OLLAMA_STRATEGY=aggro-burn
```

## Start order

1. Start Ollama.
2. Start the local Node bridge from `/Users/chris.duke/Source/tmnt_ai`.
3. Start the XMage server from this fork.
4. Create a game and choose `Computer - ollama` as the AI opponent.

## Notes

- The new player type is registered in `Mage.Server/config/config.xml`.
- The Java bridge client lives in `Mage.Server.Plugins/Mage.Player.AI.MA/src/mage/player/ai/`.
- This integration is intentionally conservative. It does not yet replace the entire XMage AI pipeline.
