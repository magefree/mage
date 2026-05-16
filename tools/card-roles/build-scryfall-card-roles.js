#!/usr/bin/env node

const fs = require("node:fs");
const https = require("node:https");
const path = require("node:path");
const zlib = require("node:zlib");

const USER_AGENT = "xmage-card-role-backfill/0.1 (https://github.com/magefree/mage)";
const ACCEPT = "application/json;q=0.9,*/*;q=0.8";
const DEFAULT_BULK_TYPE = "all_cards";
const DEFAULT_INPUT = path.join(".research", "scryfall-backfill", "raw", "scryfall-all_cards.json");
const DEFAULT_OUTPUT_DIR = path.join(".research", "scryfall-backfill", "roles");
const DEFAULT_RUNTIME_OUTPUT = path.join("Mage", "src", "main", "resources", "card-roles", "scryfall-card-roles-runtime.json.gz");
const SUBTYPE_SOURCE = path.join("Mage", "src", "main", "java", "mage", "constants", "SubType.java");

const DECK_ROLES = [
  "THREAT",
  "CHEAP_THREAT",
  "LARGE_THREAT",
  "REMOVAL",
  "BOARD_WIPE",
  "CARD_DRAW",
  "RAMP",
  "MANA_FIXING",
  "TOKEN_MAKER",
  "SACRIFICE_OUTLET",
  "SACRIFICE_FODDER_PROVIDER",
  "SACRIFICE_FODDER",
  "DEATH_PAYOFF",
  "GRAVEYARD_RECURSION",
  "DISCARD_OR_SELF_MILL",
  "LIFE_GAIN",
  "LIFE_GAIN_PAYOFF",
  "ARTIFACT_OR_ENCHANTMENT_PAYOFF",
  "PLUS_ONE_COUNTER_MAKER",
  "PLUS_ONE_COUNTER_PAYOFF",
  "X_SPELL",
  "X_SPELL_PAYOFF",
  "COUNTER_OR_PROTECTION",
  "COMBAT_TRICK"
];

const FEATURE_SIGNALS = [
  ["verb:add", /\badd(?:s|ed|ing)?\b/],
  ["verb:attach", /\battach(?:es|ed|ing)?\b/],
  ["verb:attack", /\battack(?:s|ed|ing)?\b/],
  ["verb:cast", /\bcast(?:s|ing)?\b/],
  ["verb:copy", /\bcop(?:y|ies|ied)\b/],
  ["verb:counter", /\bcounter(?:s|ed|ing)?\b/],
  ["verb:create", /\bcreat(?:e|es|ed|ing)\b/],
  ["verb:destroy", /\bdestroy(?:s|ed|ing)?\b/],
  ["verb:discard", /\bdiscard(?:s|ed|ing)?\b/],
  ["verb:draw", /\bdraw(?:s|ing)?|drawn\b/],
  ["verb:exile", /\bexil(?:e|es|ed|ing)\b/],
  ["verb:fight", /\bfight(?:s|ing)?|fought\b/],
  ["verb:gain", /\bgain(?:s|ed|ing)?\b/],
  ["verb:mill", /\bmill(?:s|ed|ing)?\b/],
  ["verb:prevent", /\bprevent(?:s|ed|ing)?\b/],
  ["verb:proliferate", /\bproliferat(?:e|es|ed|ing)\b/],
  ["verb:return", /\breturn(?:s|ed|ing)?\b/],
  ["verb:sacrifice", /\bsacrific(?:e|es|ed|ing)\b/],
  ["verb:scry", /\bscr(?:y|ies|ied)\b/],
  ["verb:search", /\bsearch(?:es|ed|ing)?\b/],
  ["verb:surveil", /\bsurveil(?:s|ed|ing)?\b/],
  ["verb:tap", /\btap(?:s|ped|ping)?\b/],
  ["object:artifact", /\bartifacts?\b/],
  ["object:card", /\bcards?\b/],
  ["object:creature", /\bcreatures?\b/],
  ["object:enchantment", /\benchantments?\b/],
  ["object:graveyard", /\bgraveyards?\b/],
  ["object:land", /\blands?\b/],
  ["object:library", /\blibraries|library\b/],
  ["object:mana", /\bmana\b/],
  ["object:opponent", /\bopponents?\b/],
  ["object:permanent", /\bpermanents?\b/],
  ["object:player", /\bplayers?\b/],
  ["object:spell", /\bspells?\b/],
  ["object:token", /\btokens?\b/],
  ["zone:battlefield", /\bbattlefield\b/],
  ["zone:exile", /\bexile\b/],
  ["zone:graveyard", /\bgraveyard\b/],
  ["zone:hand", /\bhand\b/],
  ["zone:library", /\blibrary\b/],
  ["counter:+1/+1", /\+1\/\+1 counter/],
  ["counter:-1/-1", /-1\/-1 counter/],
  ["resource:treasure", /\btreasure\b/],
  ["resource:clue", /\bclue\b/],
  ["resource:food", /\bfood\b/],
  ["resource:blood", /\bblood\b/],
  ["scales:x", /\{x\}|\bx\b[^.]*\bspent\b/],
  ["duration:end_of_turn", /\buntil end of turn\b/]
];

const ROLE_DESCRIPTIONS = {
  THREAT: "Creature or planeswalker that can pressure opponents or meaningfully occupy the board.",
  CHEAP_THREAT: "Low-mana creature pressure or early board presence.",
  LARGE_THREAT: "High-mana or high-power creature threat.",
  REMOVAL: "Destroys, exiles, bounces, counters, forces sacrifice, fights, or deals targeted creature/player/permanent damage.",
  BOARD_WIPE: "Broadly affects all or each creatures/permanents/spells.",
  CARD_DRAW: "Draws cards, loots/rummages, impulse-draws, or otherwise gives direct card access.",
  RAMP: "Increases available mana or land count beyond ordinary land drops.",
  MANA_FIXING: "Improves color access, searches lands, or produces multiple colors.",
  TOKEN_MAKER: "Creates tokens or token-like predefined game objects.",
  SACRIFICE_OUTLET: "Lets its controller sacrifice permanents/cards as cost or effect.",
  SACRIFICE_FODDER_PROVIDER: "Creates expendable permanents suitable for sacrifice synergies.",
  SACRIFICE_FODDER: "Is itself expendable, recursive, or death-value material for sacrifice synergies.",
  DEATH_PAYOFF: "Rewards creatures/permanents dying or going to graveyard.",
  GRAVEYARD_RECURSION: "Returns, casts, or plays cards from graveyard.",
  DISCARD_OR_SELF_MILL: "Fills graveyard or discards cards under own control.",
  LIFE_GAIN: "Gains life.",
  LIFE_GAIN_PAYOFF: "Rewards life gain.",
  ARTIFACT_OR_ENCHANTMENT_PAYOFF: "Rewards artifacts/enchantments or cares about controlling/casting them.",
  PLUS_ONE_COUNTER_MAKER: "Creates, moves, doubles, or proliferates +1/+1 counters.",
  PLUS_ONE_COUNTER_PAYOFF: "Rewards or spends +1/+1 counters.",
  X_SPELL: "Has X in its mana cost or core scaling cost.",
  X_SPELL_PAYOFF: "Rewards spells with X.",
  COUNTER_OR_PROTECTION: "Counters spells/abilities or protects own objects/players.",
  COMBAT_TRICK: "Instant/sorcery combat modifier, pump, fight, or temporary combat keyword."
};

function parseArgs(argv) {
  const args = {
    input: DEFAULT_INPUT,
    outputDir: DEFAULT_OUTPUT_DIR,
    runtimeOutput: DEFAULT_RUNTIME_OUTPUT,
    includeDigital: false,
    includeNonEnglish: false,
    download: false,
    refreshRaw: false
  };
  for (let i = 2; i < argv.length; i++) {
    const arg = argv[i];
    const next = argv[i + 1];
    if (arg === "--input" && next) {
      args.input = next;
      i++;
    } else if (arg === "--out" && next) {
      args.outputDir = next;
      i++;
    } else if (arg === "--runtime-out" && next) {
      args.runtimeOutput = next;
      i++;
    } else if (arg === "--include-digital") {
      args.includeDigital = true;
    } else if (arg === "--include-non-english") {
      args.includeNonEnglish = true;
    } else if (arg === "--download") {
      args.download = true;
    } else if (arg === "--refresh-raw") {
      args.refreshRaw = true;
    } else if (arg === "--help") {
      printHelp();
      process.exit(0);
    } else {
      throw new Error(`Unknown argument: ${arg}`);
    }
  }
  return args;
}

function printHelp() {
  console.log(`Usage: node tools/card-roles/build-scryfall-card-roles.js [options]

Builds offline AI card roles from Scryfall all_cards bulk data.

Options:
  --input <file>          Raw Scryfall all_cards JSON. Default: ${DEFAULT_INPUT}
  --out <dir>             Research output directory for reports/full data. Default: ${DEFAULT_OUTPUT_DIR}
  --runtime-out <file>    Runtime gzip resource to write. Default: ${DEFAULT_RUNTIME_OUTPUT}
  --include-digital       Include digital-only printings.
  --include-non-english   Include non-English printings.
  --download              Download Scryfall all_cards if --input is missing.
  --refresh-raw           Re-download the Scryfall all_cards file. Implies --download.
  --help                  Show this help.
`);
}

function requestJson(url) {
  return new Promise((resolve, reject) => {
    const req = https.get(url, {
      headers: {
        "User-Agent": USER_AGENT,
        "Accept": ACCEPT
      }
    }, (res) => {
      const chunks = [];
      res.on("data", (chunk) => chunks.push(chunk));
      res.on("end", () => {
        const body = Buffer.concat(chunks).toString("utf8");
        if (res.statusCode < 200 || res.statusCode >= 300) {
          reject(new Error(`HTTP ${res.statusCode} for ${url}: ${body}`));
          return;
        }
        try {
          resolve(JSON.parse(body));
        } catch (error) {
          reject(new Error(`Invalid JSON from ${url}: ${error.message}`));
        }
      });
    });
    req.on("error", reject);
    req.setTimeout(120000, () => {
      req.destroy(new Error(`Timed out fetching ${url}`));
    });
  });
}

function downloadFile(url, dest) {
  return new Promise((resolve, reject) => {
    const tmp = `${dest}.tmp`;
    const file = fs.createWriteStream(tmp);
    const req = https.get(url, {
      headers: {
        "User-Agent": USER_AGENT,
        "Accept": ACCEPT
      }
    }, (res) => {
      if (res.statusCode < 200 || res.statusCode >= 300) {
        file.close();
        fs.rmSync(tmp, { force: true });
        reject(new Error(`HTTP ${res.statusCode} downloading ${url}`));
        return;
      }
      res.pipe(file);
      file.on("finish", () => {
        file.close(() => {
          fs.renameSync(tmp, dest);
          resolve();
        });
      });
    });
    req.on("error", (error) => {
      file.close();
      fs.rmSync(tmp, { force: true });
      reject(error);
    });
    req.setTimeout(1800000, () => {
      req.destroy(new Error(`Timed out downloading ${url}`));
    });
  });
}

async function ensureRawInput(input, download, refreshRaw) {
  if (fs.existsSync(input) && !refreshRaw) {
    return { reused: true };
  }

  if (!download && !refreshRaw) {
    throw new Error(`Raw Scryfall bulk file not found: ${input}. Pass --download to fetch it from Scryfall.`);
  }

  ensureDir(path.dirname(input));
  console.log(`Raw Scryfall bulk file ${refreshRaw ? "refresh requested" : "not found"}: ${input}`);
  console.log("Loading Scryfall bulk metadata...");
  const bulkInfo = await requestJson("https://api.scryfall.com/bulk-data");
  const bulk = (bulkInfo.data || []).find((item) => item.type === DEFAULT_BULK_TYPE);
  if (!bulk) {
    throw new Error(`Unable to find Scryfall bulk type: ${DEFAULT_BULK_TYPE}`);
  }

  console.log(`Downloading ${bulk.name} to ${input}...`);
  await downloadFile(bulk.download_uri, input);
  return {
    reused: false,
    bulkId: bulk.id,
    bulkName: bulk.name,
    bulkUpdatedAt: bulk.updated_at,
    bulkCompressedSize: bulk.compressed_size,
    bulkContentType: bulk.content_type,
    downloadUri: bulk.download_uri
  };
}

async function* parseJsonArrayFile(file) {
  const stream = fs.createReadStream(file, { encoding: "utf8", highWaterMark: 1024 * 1024 });
  let started = false;
  let depth = 0;
  let inString = false;
  let escaped = false;
  let objectText = "";

  for await (const chunk of stream) {
    for (let i = 0; i < chunk.length; i++) {
      const ch = chunk[i];
      if (!started) {
        if (ch === "[") {
          started = true;
        }
        continue;
      }
      if (depth === 0) {
        if (ch === "{") {
          depth = 1;
          objectText = "{";
        } else if (ch === "]") {
          return;
        }
        continue;
      }
      objectText += ch;
      if (inString) {
        if (escaped) {
          escaped = false;
        } else if (ch === "\\") {
          escaped = true;
        } else if (ch === "\"") {
          inString = false;
        }
        continue;
      }
      if (ch === "\"") {
        inString = true;
      } else if (ch === "{") {
        depth++;
      } else if (ch === "}") {
        depth--;
        if (depth === 0) {
          yield JSON.parse(objectText);
          objectText = "";
        }
      }
    }
  }
  if (depth !== 0) {
    throw new Error(`Unexpected end of JSON while parsing ${file}`);
  }
}

function ensureDir(dir) {
  fs.mkdirSync(dir, { recursive: true });
}

function printKey(card) {
  return `${card.set.toUpperCase()}:${card.collector_number}:${card.name}`;
}

function normalizeText(value) {
  return (value || "")
    .toLowerCase()
    .replace(/\u2212/g, "-")
    .replace(/\([^()]*\)/g, " ")
    .replace(/\s+/g, " ")
    .trim();
}

function joinedOracle(card) {
  const parts = [];
  if (card.type_line) {
    parts.push(card.type_line);
  }
  if (card.oracle_text) {
    parts.push(card.oracle_text);
  }
  for (const face of card.card_faces || []) {
    if (face.type_line) {
      parts.push(face.type_line);
    }
    if (face.oracle_text) {
      parts.push(face.oracle_text);
    }
  }
  return normalizeText(parts.join("\n"));
}

function joinedRules(card) {
  const parts = [];
  if (card.oracle_text) {
    parts.push(card.oracle_text);
  }
  for (const face of card.card_faces || []) {
    if (face.oracle_text) {
      parts.push(face.oracle_text);
    }
  }
  return normalizeText(parts.join("\n"));
}

function joinedManaCost(card) {
  const costs = [card.mana_cost || ""];
  for (const face of card.card_faces || []) {
    costs.push(face.mana_cost || "");
  }
  return costs.join(" ");
}

function typeText(card) {
  return normalizeText([
    card.type_line || "",
    ...(card.card_faces || []).map((face) => face.type_line || "")
  ].join(" "));
}

function loadCreatureTypes() {
  try {
    const source = fs.readFileSync(SUBTYPE_SOURCE, "utf8");
    return Array.from(source.matchAll(/^[ \t]*[A-Z0-9_]+\(("[^"]+"),\s*SubTypeSet\.CreatureType/gm))
      .map((match) => JSON.parse(match[1]))
      .sort((a, b) => b.length - a.length || a.localeCompare(b));
  } catch (error) {
    return ["Dragon", "Sliver", "Elf", "Goblin", "Zombie", "Vampire", "Wizard", "Human", "Merfolk"];
  }
}

const CREATURE_TYPES = loadCreatureTypes();

function isType(card, type) {
  return new RegExp(`\\b${type.toLowerCase()}\\b`).test(typeText(card));
}

function isInstantOrSorcery(card) {
  return isType(card, "instant") || isType(card, "sorcery");
}

function cmc(card) {
  return Number.isFinite(card.cmc) ? card.cmc : 0;
}

function hasXCost(card) {
  return /\{x\}/i.test(joinedManaCost(card));
}

function addRole(result, role, confidence, reason) {
  if (!DECK_ROLES.includes(role)) {
    throw new Error(`Unknown role: ${role}`);
  }
  const previous = result.roles[role];
  if (!previous || scoreConfidence(confidence) > scoreConfidence(previous.confidence)) {
    result.roles[role] = { confidence, reasons: [reason] };
  } else if (previous && !previous.reasons.includes(reason)) {
    previous.reasons.push(reason);
  }
}

function scoreConfidence(confidence) {
  switch (confidence) {
    case "high": return 3;
    case "medium": return 2;
    default: return 1;
  }
}

function classifyCard(card) {
  const rules = joinedOracle(card);
  const rulesOnly = joinedRules(card);
  const types = typeText(card);
  const result = {
    roles: {},
    features: extractFeatures(card, rules, types),
    mechanics: extractMechanics(card, rulesOnly)
  };

  const creature = /\bcreature\b/.test(types);
  const planeswalker = /\bplaneswalker\b/.test(types);
  const land = /\bland\b/.test(types);
  const artifact = /\bartifact\b/.test(types);
  const enchantment = /\benchantment\b/.test(types);
  const instantOrSorcery = isInstantOrSorcery(card);
  const xCost = hasXCost(card);

  if (creature || planeswalker) {
    addRole(result, "THREAT", "medium", "creature or planeswalker");
  }
  if (creature && cmc(card) <= 2 && !xCost) {
    addRole(result, "CHEAP_THREAT", "medium", "creature with mana value <= 2");
  }
  if (creature && (cmc(card) >= 5 || numericPower(card) >= 4)) {
    addRole(result, "LARGE_THREAT", "medium", "large creature by mana value or power");
  }
  if (xCost) {
    addRole(result, "X_SPELL", "high", "mana cost contains {X}");
  }

  if (isBoardWipe(rules)) {
    addRole(result, "BOARD_WIPE", "high", "all/each broad removal wording");
    addRole(result, "REMOVAL", "high", "board wipe is removal");
  }
  if (isRemoval(rules)) {
    addRole(result, "REMOVAL", "medium", "removal wording");
  }
  if (isCardDraw(rules)) {
    addRole(result, "CARD_DRAW", "medium", "card draw or card access wording");
  }
  if (isRamp(card, rules, land, artifact, creature)) {
    addRole(result, "RAMP", land ? "low" : "medium", "mana or land acceleration wording");
  }
  if (isManaFixing(card, rules, land)) {
    addRole(result, "MANA_FIXING", "medium", "color fixing or land search wording");
  }
  if (isTokenMaker(rules)) {
    addRole(result, "TOKEN_MAKER", "high", "creates token");
    addRole(result, "SACRIFICE_FODDER_PROVIDER", "medium", "token maker creates expendable permanents");
  }
  if (isSacrificeOutlet(rules, land)) {
    addRole(result, "SACRIFICE_OUTLET", "medium", "controller can sacrifice own resources");
  }
  if (isDeathPayoff(rules)) {
    addRole(result, "DEATH_PAYOFF", "medium", "dies/graveyard payoff wording");
  }
  if (isGraveyardRecursion(rules)) {
    addRole(result, "GRAVEYARD_RECURSION", "medium", "returns/casts/plays from graveyard");
  }
  if (isDiscardOrSelfMill(rules)) {
    addRole(result, "DISCARD_OR_SELF_MILL", "medium", "discard, mill, surveil, or graveyard setup");
  }
  if (isLifeGain(rules)) {
    addRole(result, "LIFE_GAIN", "medium", "life gain wording");
  }
  if (isLifeGainPayoff(rules)) {
    addRole(result, "LIFE_GAIN_PAYOFF", "high", "life gain payoff wording");
  }
  if (isArtifactOrEnchantmentPayoff(rules, artifact, enchantment)) {
    addRole(result, "ARTIFACT_OR_ENCHANTMENT_PAYOFF", "medium", "artifact/enchantment payoff wording");
  }
  if (isPlusOneCounterMaker(rules)) {
    addRole(result, "PLUS_ONE_COUNTER_MAKER", "medium", "+1/+1 counter creation/proliferation wording");
  }
  if (isPlusOneCounterPayoff(rules)) {
    addRole(result, "PLUS_ONE_COUNTER_PAYOFF", "medium", "+1/+1 counter payoff wording");
  }
  if (isXSpellPayoff(rules)) {
    addRole(result, "X_SPELL_PAYOFF", "high", "X-spell payoff wording");
  }
  if (isCounterOrProtection(rules)) {
    addRole(result, "COUNTER_OR_PROTECTION", "medium", "counterspell or protection wording");
  }
  if (instantOrSorcery && isCombatTrick(rules)) {
    addRole(result, "COMBAT_TRICK", "medium", "instant/sorcery combat modifier wording");
  }

  return result;
}

function numericPower(card) {
  const values = [];
  if (card.power != null) {
    values.push(card.power);
  }
  for (const face of card.card_faces || []) {
    if (face.power != null) {
      values.push(face.power);
    }
  }
  return values
    .map((value) => Number.parseInt(String(value), 10))
    .filter(Number.isFinite)
    .reduce((max, value) => Math.max(max, value), 0);
}

function isBoardWipe(rules) {
  return /\b(?:destroy|exile|return|tap)\s+all\b/.test(rules)
    || /\b(?:destroy|exile|return|tap)\s+each\b/.test(rules)
    || /\beach creature\b[^.]*\b(?:gets -|damage|destroyed|exiled|sacrifices?)\b/.test(rules)
    || /\ball creatures\b[^.]*\b(?:get -|damage|destroyed|exiled|sacrifices?)\b/.test(rules)
    || /\bdeals? \d+ damage to each creature\b/.test(rules)
    || /\bdeals? x damage to each creature\b/.test(rules)
    || /\bcreatures get -\d+\/-\d+\b/.test(rules)
    || /\ball nonland permanents\b/.test(rules)
    || /\beach nonland permanent\b/.test(rules);
}

function isRemoval(rules) {
  return /\b(?:destroy|exile|return|tap)\s+(?:up to |another |target |each |all )?(?:[\w-]+[, ]+){0,5}(?:artifact|creature|enchantment|permanent|planeswalker|battle|land|spell)\b/.test(rules)
    || /\btarget (?:player|opponent|creature|planeswalker|battle|permanent)[^.]*\b(?:sacrifices?|gets -|loses all abilities|damage)\b/.test(rules)
    || /\b(?:deals?|deal) (?:\d+|x|that much) damage\b[^.]*\b(?:any target|target|targets|creature|player|opponent|planeswalker|battle)\b/.test(rules)
    || /\b(?:damage|damage divided as you choose)\b[^.]*\b(?:one or two|any number of|target|targets)\b/.test(rules)
    || /\bbury\b[^.]*\b(?:target|creature|artifact|permanent|land|enchantment)\b/.test(rules)
    || /\b(?:each player|target player|each opponent|target opponent)\b[^.]*\bsacrifices?\b[^.]*\b(?:creature|permanent|artifact|enchantment|planeswalker)\b/.test(rules)
    || /\b(?:counter target|counter all|counter each)\b/.test(rules)
    || /\bfight(?:s)? target creature\b/.test(rules)
    || /\btarget creature fights\b/.test(rules);
}

function isCardDraw(rules) {
  return /\bdraws? (?:a card|one card|two cards|three cards|\d+ cards|x cards|cards equal|that many cards)\b/.test(rules)
    || /\b(?:look at|reveal) the top [^.]*\b(?:put|play|cast)[^.]*\b(?:hand|exile|library)\b/.test(rules)
    || /\blook at the top [^.]*\.[^.]*\bput (?:one|a card|that card|any number)[^.]*\b(?:hand|exile|top|bottom)\b/.test(rules)
    || /\bexile the top [^.]*\b(?:you may play|you may cast|until the end of your next turn)\b/.test(rules)
    || /\bsearch your library\b[^.]*\b(?:for a card|for an? .* card)\b[^.]*\b(?:put|reveal)\b[^.]*\b(?:hand|top of your library|exile)\b/.test(rules)
    || /\bwhenever [^.]*draw a card\b/.test(rules)
    || /\bconnive\b/.test(rules);
}

function isRamp(card, rules, land, artifact, creature) {
  if (!land && /\badd\b[^.]*\b(?:mana|\{[cwubrg]\})\b/.test(rules)) {
    return true;
  }
  if (!land && /\bsearch your library\b[^.]*\bland\b[^.]*\bput (?:it|one|a land|that card|them|those cards)[^.]* onto the battlefield\b/.test(rules)) {
    return true;
  }
  if (/\bplay an additional land\b|\bplay additional lands\b|you may play \d+ additional lands/.test(rules)) {
    return true;
  }
  if (!land && /\buntap (?:target |up to |all )?(?:land|lands)\b/.test(rules)) {
    return true;
  }
  if ((artifact || creature) && (card.produced_mana || []).length > 0) {
    return true;
  }
  return false;
}

function isManaFixing(card, rules, land) {
  if ((card.produced_mana || []).length > 1) {
    return true;
  }
  return /\badd one mana of any (?:color|one color|type)\b/.test(rules)
    || /\badd (?:two|three|x)? ?mana in any combination of colors\b/.test(rules)
    || /\bsearch your library\b[^.]*\b(?:basic land|land card|plains|island|swamp|mountain|forest)\b/.test(rules)
    || (land && /\bsacrifice\b[^.]*\bsearch your library\b[^.]*\bland\b/.test(rules));
}

function isTokenMaker(rules) {
  return /\bcreate\b[^.]*\btoken/.test(rules)
    || /\bincubat(?:e|es|ed|ing)\b/.test(rules)
    || /\bamass\b/.test(rules)
    || /\bmanifest\b/.test(rules)
    || /\bclue token|food token|treasure token|blood token|map token/.test(rules);
}

function isSacrificeOutlet(rules, land) {
  if (!/\bsacrifice\b/.test(rules)) {
    return false;
  }
  if (land && /\bsacrifice\b[^.]*\bsearch your library\b[^.]*\bland\b/.test(rules)) {
    return false;
  }
  if (/\b(?:opponent|opponents|each player|target player)\b[^.]*\bsacrifices?\b/.test(rules)) {
    return false;
  }
  return /\b(?:sacrifice|sacrificing)\s+(?:x|all|any number of|one or more|another|a|an|one|two|three|four|five|six|seven|eight|nine|ten|this)\b[^.]*\b(?:creature|permanent|artifact|enchantment|token|treasure|clue|food|blood|land|card)s?\b/.test(rules)
    || /\bas an additional cost[^.]*\bsacrifice\b/.test(rules)
    || /\bwhenever you sacrifice\b/.test(rules);
}

function isDeathPayoff(rules) {
  return /\bwhenever\b[^.]*\b(?:dies|die|is put into a graveyard|are put into a graveyard)\b/.test(rules)
    || /\bwhenever\b[^.]*\bcreature card leaves your graveyard\b/.test(rules)
    || /\bif [^.]* died this turn\b/.test(rules)
    || /\bfor each creature card in your graveyard\b/.test(rules);
}

function isGraveyardRecursion(rules) {
  return /\b(?:return|cast|play|put)\b[^.]*\bfrom (?:your|a|any) graveyard\b/.test(rules)
    || /\bescape\b|\bflashback\b|\bjump-start\b|\bdisturb\b|\brecurring\b/.test(rules);
}

function isDiscardOrSelfMill(rules) {
  return /\bmill (?:a card|one card|two cards|three cards|\d+ cards|x cards)\b/.test(rules)
    || /\bsurveil\b/.test(rules)
    || /\bdiscard (?:a card|one card|two cards|your hand|that card)\b/.test(rules)
    || /\bput the top [^.]* cards? of your library into your graveyard\b/.test(rules);
}

function isLifeGain(rules) {
  return /\byou gain (?:\d+|x|that much|life equal|one|two|three|four|five) life\b/.test(rules)
    || /\bgain life\b/.test(rules)
    || /\blifelink\b/.test(rules);
}

function isLifeGainPayoff(rules) {
  return /\bwhenever you gain life\b|\bif you gained life\b|\beach time you gain life\b/.test(rules);
}

function isArtifactOrEnchantmentPayoff(rules, artifact, enchantment) {
  if (artifact || enchantment) {
    return false;
  }
  return /\b(?:artifact|enchantment)s? you control\b/.test(rules)
    || /\bwhenever (?:you cast|an? .* enters)[^.]*\b(?:artifact|enchantment)\b/.test(rules)
    || /\bfor each (?:artifact|enchantment)\b/.test(rules);
}

function isPlusOneCounterMaker(rules) {
  return /\+1\/\+1 counter/.test(rules)
    && /\b(?:put|puts|add|adds|move|moves|double|doubles|distribute|proliferate)\b/.test(rules);
}

function isPlusOneCounterPayoff(rules) {
  return /\+1\/\+1 counter/.test(rules)
    && /\b(?:for each|with a \+1\/\+1 counter|with one or more \+1\/\+1 counters|has a \+1\/\+1 counter|remove a \+1\/\+1 counter|remove \d+ \+1\/\+1 counters)\b/.test(rules);
}

function isXSpellPayoff(rules) {
  return /\bspells? with \{x\}\b|\b\{x\} in (?:its|their) mana cost\b|\bx in (?:its|their) mana cost\b/.test(rules);
}

function isCounterOrProtection(rules) {
  return /\bcounter target (?:spell|activated ability|triggered ability|ability)\b/.test(rules)
    || grantsKeyword(rules, "hexproof")
    || grantsKeyword(rules, "indestructible")
    || grantsProtectionFrom(rules)
    || /\bprevent all\b[^.]*\bdamage\b/.test(rules)
    || /\bregenerate\b/.test(rules)
    || /\bphase out\b/.test(rules)
    || /\bcan't be (?:countered|destroyed|targeted)\b/.test(rules);
}

function grantsKeyword(rules, keyword) {
  return !rules.includes(`lose ${keyword}`)
    && !rules.includes(`loses ${keyword}`)
    && !rules.includes(`can't have ${keyword}`)
    && !rules.includes(`can't gain ${keyword}`)
    && new RegExp(`\\b(?:gain|gains|have|has) ${keyword}\\b`).test(rules);
}

function grantsProtectionFrom(rules) {
  return !rules.includes("lose protection from")
    && !rules.includes("loses protection from")
    && !rules.includes("can't have protection from")
    && /\b(?:gain|gains|have|has )?protection from\b/.test(rules);
}

function isCombatTrick(rules) {
  return /\bgets? \+[0-9x*]+\/[+-]?[0-9x*]+\b/.test(rules)
    || /\bgains? (?:first strike|double strike|trample|deathtouch|lifelink|flying|hexproof|indestructible)\b/.test(rules)
    || /\bfight(?:s)?\b/.test(rules);
}

function extractFeatures(card, rules, types) {
  const features = new Set();
  for (const [feature, pattern] of FEATURE_SIGNALS) {
    if (pattern.test(rules)) {
      features.add(feature);
    }
  }
  for (const type of ["artifact", "battle", "creature", "enchantment", "instant", "land", "planeswalker", "sorcery"]) {
    if (new RegExp(`\\b${type}\\b`).test(types)) {
      features.add(`type:${type}`);
    }
  }
  for (const keyword of card.keywords || []) {
    features.add(`keyword:${keyword.toLowerCase().replace(/[^a-z0-9]+/g, "_").replace(/^_|_$/g, "")}`);
  }
  if ((card.produced_mana || []).length > 0) {
    features.add(`produces:${card.produced_mana.join("")}`);
  }
  return Array.from(features).sort();
}

function extractMechanics(card, rules) {
  const mechanics = new Set();
  for (const subtype of ownCreatureSubtypes(card)) {
    mechanics.add(`SUBTYPE:${subtype}`);
  }
  for (const subtype of CREATURE_TYPES) {
    if (referencesSubtype(rules, subtype)) {
      mechanics.add(`TRIBAL:${subtype}`);
    }
  }
  if (rules.includes("choose a creature type")) {
    mechanics.add("TRIBAL:ANY");
  }
  if (isTokenMaker(rules)) {
    mechanics.add("TOKENS");
  }
  if (rules.includes("sacrifice ")) {
    mechanics.add("SACRIFICE");
  }
  if (rules.includes("+1/+1 counter")) {
    mechanics.add("PLUS_ONE_COUNTERS");
  }
  if (rules.includes("graveyard")) {
    mechanics.add("GRAVEYARD");
  }
  if (/\bgain(?:s|ed)? life\b/.test(rules)) {
    mechanics.add("LIFE_GAIN");
  }
  if (/\b(?:artifact|treasure|clue|food)\b/.test(rules)) {
    mechanics.add("ARTIFACTS");
  }
  return Array.from(mechanics).sort();
}

function ownCreatureSubtypes(card) {
  const subtypes = new Set();
  const typeLines = [card.type_line || "", ...(card.card_faces || []).map((face) => face.type_line || "")];
  for (const typeLine of typeLines) {
    if (!/\b(?:creature|kindred)\b/i.test(typeLine) || !typeLine.includes("—")) {
      continue;
    }
    const subtypeText = normalizeText(typeLine.split("—").slice(1).join(" "));
    for (const subtype of CREATURE_TYPES) {
      if (referencesSubtype(subtypeText, subtype)) {
        subtypes.add(subtype);
      }
    }
  }
  return Array.from(subtypes).sort();
}

function referencesSubtype(text, subtype) {
  const singular = escapeRegExp(subtype.toLowerCase());
  const plural = escapeRegExp(pluralizeSubtype(subtype).toLowerCase());
  return new RegExp(`\\b(?:${singular}|${plural})\\b`).test(text);
}

function pluralizeSubtype(subtype) {
  return subtype.endsWith("y") ? `${subtype.slice(0, -1)}ies` : `${subtype}s`;
}

function escapeRegExp(value) {
  return value.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
}

function mergeRoleMaps(target, source) {
  for (const [role, value] of Object.entries(source)) {
    if (!target[role] || scoreConfidence(value.confidence) > scoreConfidence(target[role].confidence)) {
      target[role] = {
        confidence: value.confidence,
        reasons: Array.from(new Set(value.reasons))
      };
    } else {
      target[role].reasons = Array.from(new Set([...target[role].reasons, ...value.reasons]));
    }
  }
}

function addSample(samples, key, value, limit = 8) {
  if (!samples[key]) {
    samples[key] = [];
  }
  if (samples[key].length < limit && !samples[key].includes(value)) {
    samples[key].push(value);
  }
}

function featureCombo(features) {
  return features
    .filter((feature) => !feature.startsWith("type:"))
    .filter((feature) => !feature.startsWith("keyword:"))
    .slice(0, 6)
    .join(" + ") || "(type/keyword only)";
}

function roleNames(roleMap) {
  return Object.keys(roleMap).sort();
}

function increment(map, key, amount = 1) {
  map[key] = (map[key] || 0) + amount;
}

function writeJsonGz(file, data) {
  fs.writeFileSync(file, zlib.gzipSync(JSON.stringify(data)));
}

function displayPath(file) {
  const relative = path.relative(process.cwd(), file);
  if (relative && !relative.startsWith("..") && !path.isAbsolute(relative)) {
    return relative;
  }
  return file;
}

function renderReport(summary, topUnclassified, topRoles, topFeatures, metadata) {
  const lines = [];
  lines.push("# Scryfall AI Role Backfill Report");
  lines.push("");
  lines.push(`Generated: ${metadata.generatedAt}`);
  lines.push(`Input: ${metadata.input}`);
  lines.push("");
  lines.push("## Summary");
  lines.push("");
  lines.push(`- Print records scanned: ${summary.scannedPrints}`);
  lines.push(`- Print records included: ${summary.includedPrints}`);
  lines.push(`- Oracle IDs classified: ${summary.oracleIds}`);
  lines.push(`- Oracle IDs with at least one role: ${summary.oracleIdsWithRoles}`);
  lines.push(`- Oracle IDs with no role: ${summary.oracleIdsWithoutRoles}`);
  lines.push("");
  lines.push("## Role Counts");
  lines.push("");
  lines.push("| Role | Oracle IDs |");
  lines.push("| --- | ---: |");
  for (const [role, count] of topRoles) {
    lines.push(`| ${role} | ${count} |`);
  }
  lines.push("");
  lines.push("## Common Unclassified Feature Combos");
  lines.push("");
  lines.push("| Features | Count | Samples |");
  lines.push("| --- | ---: | --- |");
  for (const entry of topUnclassified) {
    lines.push(`| ${entry.combo.replace(/\|/g, "\\|")} | ${entry.count} | ${entry.samples.join(", ").replace(/\|/g, "\\|")} |`);
  }
  lines.push("");
  lines.push("## Common Raw Features");
  lines.push("");
  lines.push("| Feature | Oracle IDs |");
  lines.push("| --- | ---: |");
  for (const [feature, count] of topFeatures) {
    lines.push(`| ${feature} | ${count} |`);
  }
  lines.push("");
  lines.push("## Role Definitions");
  lines.push("");
  for (const role of DECK_ROLES) {
    lines.push(`- \`${role}\`: ${ROLE_DESCRIPTIONS[role]}`);
  }
  lines.push("");
  return lines.join("\n");
}

async function main() {
  const args = parseArgs(process.argv);
  const input = path.resolve(args.input);
  const outputDir = path.resolve(args.outputDir);
  const runtimeOutput = path.resolve(args.runtimeOutput);
  ensureDir(outputDir);
  ensureDir(path.dirname(runtimeOutput));
  const rawInput = await ensureRawInput(input, args.download, args.refreshRaw);

  const metadata = {
    generatedAt: new Date().toISOString(),
    input: displayPath(input),
    rawInput,
    includeDigital: args.includeDigital,
    includeNonEnglish: args.includeNonEnglish,
    roles: DECK_ROLES,
    schema: {
      byOracleId: "oracle_id -> merged roles/features/print keys for all included printings",
      byPrintKey: "SET:collector_number:name -> oracle_id and roles for that print",
      role: "role values match mage.cards.decks.analysis.DeckRole"
    }
  };

  const byOracleId = {};
  const byPrintKey = {};
  const roleCounts = {};
  const featureCounts = {};
  const unclassifiedCombos = {};
  const unclassifiedSamples = {};

  const summary = {
    scannedPrints: 0,
    includedPrints: 0,
    oracleIds: 0,
    oracleIdsWithRoles: 0,
    oracleIdsWithoutRoles: 0
  };

  console.log(`Input: ${input}`);
  console.log(`Output: ${outputDir}`);
  console.log("Classifying Scryfall print records...");

  for await (const card of parseJsonArrayFile(input)) {
    summary.scannedPrints++;
    if (!args.includeNonEnglish && card.lang !== "en") {
      continue;
    }
    if (!args.includeDigital && card.digital) {
      continue;
    }
    if (!card.oracle_id) {
      continue;
    }

    summary.includedPrints++;
    const key = printKey(card);
    const classified = classifyCard(card);
    const roles = roleNames(classified.roles);

    byPrintKey[key] = {
      oracleId: card.oracle_id,
      scryfallId: card.id,
      name: card.name,
      set: card.set,
      collectorNumber: card.collector_number,
      roles,
      roleDetails: classified.roles,
      features: classified.features,
      mechanics: classified.mechanics
    };

    if (!byOracleId[card.oracle_id]) {
      byOracleId[card.oracle_id] = {
        oracleId: card.oracle_id,
        representativeName: card.name,
        representativeTypeLine: card.type_line,
        roles: {},
        features: [],
        mechanics: [],
        printKeys: []
      };
    }
    const aggregate = byOracleId[card.oracle_id];
    mergeRoleMaps(aggregate.roles, classified.roles);
    aggregate.features = Array.from(new Set([...aggregate.features, ...classified.features])).sort();
    aggregate.mechanics = Array.from(new Set([...aggregate.mechanics, ...classified.mechanics])).sort();
    aggregate.printKeys.push(key);

    if (summary.includedPrints % 10000 === 0) {
      console.log(`  classified ${summary.includedPrints} print records...`);
    }
  }

  for (const aggregate of Object.values(byOracleId)) {
    summary.oracleIds++;
    aggregate.roleList = roleNames(aggregate.roles);
    aggregate.printKeys = Array.from(new Set(aggregate.printKeys)).sort();
    if (aggregate.roleList.length > 0) {
      summary.oracleIdsWithRoles++;
      for (const role of aggregate.roleList) {
        increment(roleCounts, role);
      }
    } else {
      summary.oracleIdsWithoutRoles++;
      const combo = featureCombo(aggregate.features);
      increment(unclassifiedCombos, combo);
      addSample(unclassifiedSamples, combo, aggregate.representativeName);
    }
    for (const feature of aggregate.features) {
      increment(featureCounts, feature);
    }
  }

  const topRoles = Object.entries(roleCounts).sort((a, b) => b[1] - a[1] || a[0].localeCompare(b[0]));
  const topFeatures = Object.entries(featureCounts).sort((a, b) => b[1] - a[1] || a[0].localeCompare(b[0])).slice(0, 100);
  const topUnclassified = Object.entries(unclassifiedCombos)
    .sort((a, b) => b[1] - a[1] || a[0].localeCompare(b[0]))
    .slice(0, 75)
    .map(([combo, count]) => ({
      combo,
      count,
      samples: unclassifiedSamples[combo] || []
    }));

  const rolesFile = path.join(outputDir, "scryfall-card-roles.json.gz");
  const runtimeFile = path.join(outputDir, "scryfall-card-roles-runtime.json.gz");
  const reportFile = path.join(outputDir, "scryfall-card-roles-report.md");
  const summaryFile = path.join(outputDir, "scryfall-card-roles-summary.json");
  const runtime = buildRuntimeIndex(metadata, byOracleId, byPrintKey);

  writeJsonGz(rolesFile, {
    metadata,
    summary,
    roleDescriptions: ROLE_DESCRIPTIONS,
    byOracleId,
    byPrintKey
  });
  writeJsonGz(runtimeFile, runtime);
  writeJsonGz(runtimeOutput, runtime);
  fs.writeFileSync(reportFile, renderReport(summary, topUnclassified, topRoles, topFeatures, metadata), "utf8");
  fs.writeFileSync(summaryFile, JSON.stringify({
    metadata,
    summary,
    roleCounts,
    topUnclassified,
    topFeatures
  }, null, 2), "utf8");

  console.log("Done.");
  console.log(`Role data: ${rolesFile}`);
  console.log(`Runtime index: ${runtimeFile}`);
  console.log(`Runtime resource: ${runtimeOutput}`);
  console.log(`Report: ${reportFile}`);
  console.log(`Summary: ${summaryFile}`);
}

function buildRuntimeIndex(metadata, byOracleId, byPrintKey) {
  const rolesByPrintKey = {};
  const rolesByName = {};
  const rolesByOracleId = {};
  const mechanicsByPrintKey = {};
  const mechanicsByName = {};
  const mechanicsByOracleId = {};

  for (const [oracleId, card] of Object.entries(byOracleId)) {
    if (card.roleList.length > 0) {
      rolesByOracleId[oracleId] = card.roleList;
      addNameRoles(rolesByName, card.representativeName, card.roleList);
    }
    if (card.mechanics.length > 0) {
      mechanicsByOracleId[oracleId] = card.mechanics;
      addNameRoles(mechanicsByName, card.representativeName, card.mechanics);
    }
  }

  for (const [key, card] of Object.entries(byPrintKey)) {
    if (card.roles.length > 0) {
      rolesByPrintKey[key] = card.roles;
      addNameRoles(rolesByName, card.name, card.roles);
    }
    if (card.mechanics.length > 0) {
      mechanicsByPrintKey[key] = card.mechanics;
      addNameRoles(mechanicsByName, card.name, card.mechanics);
    }
  }

  for (const name of Object.keys(rolesByName)) {
    rolesByName[name] = Array.from(rolesByName[name]).sort();
  }
  for (const name of Object.keys(mechanicsByName)) {
    mechanicsByName[name] = Array.from(mechanicsByName[name]).sort();
  }

  return {
    metadata: {
      generatedAt: new Date().toISOString(),
      sourceGeneratedAt: metadata.generatedAt,
      input: metadata.input,
      schemaVersion: 1
    },
    rolesByPrintKey,
    rolesByName,
    rolesByOracleId,
    mechanicsByPrintKey,
    mechanicsByName,
    mechanicsByOracleId
  };
}

function addNameRoles(rolesByName, name, roles) {
  if (!name || roles.length === 0) {
    return;
  }
  if (!rolesByName[name]) {
    rolesByName[name] = new Set();
  }
  for (const role of roles) {
    rolesByName[name].add(role);
  }
}

main().catch((error) => {
  console.error(error.stack || error.message);
  process.exit(1);
});
