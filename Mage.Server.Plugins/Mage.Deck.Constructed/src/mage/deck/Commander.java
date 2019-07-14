package mage.deck;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.filter.FilterMana;
import mage.util.ManaUtil;

import java.util.*;

/**
 * @author Plopman
 */
public class Commander extends Constructed {

    protected List<String> bannedCommander = new ArrayList<>();
    protected boolean partnerAllowed = true;

    public Commander() {
        this("Commander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Biorhythm");
        banned.add("Black Lotus");
        banned.add("Braids, Cabal Minion");
        banned.add("Channel");
        banned.add("Coalition Victory");
        banned.add("Emrakul, the Aeons Torn");
        banned.add("Erayo, Soratami Ascendant");
        banned.add("Fastbond");
        banned.add("Gifts Ungiven");
        banned.add("Griselbrand");
        banned.add("Iona, Shield of Emeria");
        banned.add("Karakas");
        banned.add("Leovold, Emissary of Trest");
        banned.add("Library of Alexandria");
        banned.add("Limited Resources");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Panoptic Mirror");
        banned.add("Paradox Engine");
        banned.add("Primeval Titan");
        banned.add("Prophet of Kruphix");
        banned.add("Recurring Nightmare");
        banned.add("Rofellos, Llanowar Emissary");
        banned.add("Sundering Titan");
        banned.add("Sway of the Stars");
        banned.add("Sylvan Primordial");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Tinker");
        banned.add("Tolarian Academy");
        banned.add("Trade Secrets");
        banned.add("Upheaval");
        banned.add("Worldfire");
        banned.add("Yawgmoth's Bargain");
    }

    public Commander(String name) {
        super(name);
    }

    @Override
    public int getDeckMinSize() {
        return 98;
    }

    @Override
    public int getSideboardMinSize() {
        return 1;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        FilterMana colorIdentity = new FilterMana();

        if (deck.getCards().size() + deck.getSideboard().size() != 100) {
            invalid.put("Deck", "Must contain " + 100 + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey()) && !anyNumberCardsAllowed.contains(entry.getKey())) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                invalid.put(bannedCard, "Banned");
                valid = false;
            }
        }

        if (deck.getSideboard().isEmpty() || deck.getSideboard().size() > 2) {
            if ((deck.getSideboard().size() > 1 && !partnerAllowed)) {
                invalid.put("Commander", "You may only have one commander");
            }
            invalid.put("Commander", "Sideboard must contain only the commander(s)");
            valid = false;
        } else {
            Set<String> commanderNames = new HashSet<>();
            for (Card commander : deck.getSideboard()) {
                commanderNames.add(commander.getName());
            }
            for (Card commander : deck.getSideboard()) {
                if (bannedCommander.contains(commander.getName())) {
                    invalid.put("Commander", "Commander banned (" + commander.getName() + ')');
                    valid = false;
                }
                if ((!commander.isCreature() || !commander.isLegendary())
                        && (!commander.isPlaneswalker() || !commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                    invalid.put("Commander", "Commander invalid (" + commander.getName() + ')');
                    valid = false;
                }
                if (deck.getSideboard().size() == 2 && !commander.getAbilities().contains(PartnerAbility.getInstance())) {
                    boolean partnersWith = false;
                    for (Ability ability : commander.getAbilities()) {
                        if (ability instanceof PartnerWithAbility
                                && commanderNames.contains(((PartnerWithAbility) ability).getPartnerName())) {
                            partnersWith = true;
                            break;
                        }
                    }
                    if (!partnersWith) {
                        invalid.put("Commander", "Commander without Partner (" + commander.getName() + ')');
                        valid = false;
                    }
                }
                ManaUtil.collectColorIdentity(colorIdentity, commander.getColorIdentity());
            }
        }

        // no needs in cards check on wrong commanders
        if (!valid) {
            return false;
        }

        for (Card card : deck.getCards()) {
            if (!ManaUtil.isColorIdentityCompatible(colorIdentity, card.getColorIdentity())) {
                invalid.put(card.getName(), "Invalid color (" + colorIdentity.toString() + ')');
                valid = false;
            }
        }
        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        return valid;
    }

    @Override
    public int getEdhPowerLevel(Deck deck) {
        if (deck == null) {
            return 0;
        }

        int edhPowerLevel = 0;
        int commanderColors = 0;
        int numberInfinitePieces = 0;

        for (Card card : deck.getCards()) {

            int thisMaxPower = 0;

            // Examine rules to work out most egregious functions in edh
            boolean anyNumberOfTarget = false;
            boolean annihilator = false;
            boolean buyback = false;
            boolean cascade = false;
            boolean cantBe = false;
            boolean cantUntap = false;
            boolean copy = false;
            boolean costLessEach = false;
            boolean createToken = false;
            boolean dredge = false;
            boolean exile = false;
            boolean exileAll = false;
            boolean counter = false;
            boolean destroy = false;
            boolean destroyAll = false;
            boolean each = false;
            boolean exalted = false;
            boolean doesntUntap = false;
            boolean drawCards = false;
            boolean evoke = false;
            boolean extraTurns = false;
            boolean flash = false;
            boolean flashback = false;
            boolean flicker = false;
            boolean gainControl = false;
            boolean hexproof = false;
            boolean infect = false;
            boolean lifeTotalBecomes = false;
            boolean mayCastForFree = false;
            boolean menace = false;
            boolean miracle = false;
            boolean overload = false;
            boolean persist = false;
            boolean preventDamage = false;
            boolean proliferate = false;
            boolean protection = false;
            boolean putUnderYourControl = false;
            boolean retrace = false;
            boolean returnFromYourGY = false;
            boolean sacrifice = false;
            boolean shroud = false;
            boolean skip = false;
            boolean sliver = false;
            boolean storm = false;
            boolean trample = false;
            boolean tutor = false;
            boolean tutorBasic = false;
            boolean twiceAs = false;
            boolean unblockable = false;
            boolean undying = false;
            boolean untapTarget = false;
            boolean wheneverEnters = false;
            boolean whenCounterThatSpell = false;
            boolean xCost = false;
            boolean youControlTarget = false;
            boolean yourOpponentsControl = false;
            boolean whenYouCast = false;

            for (String str : card.getRules()) {
                String s = str.toLowerCase(Locale.ENGLISH);
                annihilator |= s.contains("annihilator");
                anyNumberOfTarget |= s.contains("any number");
                buyback |= s.contains("buyback");
                cantUntap |= s.contains("can't untap") || s.contains("don't untap");
                cantBe |= s.contains("can't be");
                cascade |= s.contains("cascade");
                copy |= s.contains("copy");
                costLessEach |= s.contains("cost") || s.contains("less") || s.contains("each");
                counter |= s.contains("counter") && s.contains("target");
                createToken |= s.contains("create") && s.contains("token");
                destroy |= s.contains("destroy");
                destroyAll |= s.contains("destroy all");
                doesntUntap |= s.contains("doesn't untap");
                doesntUntap |= s.contains("don't untap");
                drawCards |= s.contains("draw cards");
                dredge |= s.contains("dredge");
                each |= s.contains("each");
                evoke |= s.contains("evoke");
                exalted |= s.contains("exalted");
                exile |= s.contains("exile");
                exileAll |= s.contains("exile") && s.contains(" all ");
                extraTurns |= s.contains("extra turn");
                flicker |= s.contains("exile") && s.contains("return") && s.contains("to the battlefield under");
                flash |= s.contains("flash");
                flashback |= s.contains("flashback");
                gainControl |= s.contains("gain control");
                hexproof |= s.contains("hexproof");
                infect |= s.contains("infect");
                lifeTotalBecomes |= s.contains("life total becomes");
                mayCastForFree |= s.contains("may cast") && s.contains("without paying");
                menace |= s.contains("menace");
                miracle |= s.contains("miracle");
                overload |= s.contains("overload");
                persist |= s.contains("persist");
                preventDamage |= s.contains("prevent") && s.contains("all") && s.contains("damage");
                proliferate |= s.contains("proliferate");
                protection |= s.contains("protection");
                putUnderYourControl |= s.contains("put") && s.contains("under your control");
                retrace |= s.contains("retrace");
                returnFromYourGY |= s.contains("return") && s.contains("from your graveyard");
                sacrifice |= s.contains("sacrifice");
                shroud |= s.contains("shroud");
                skip |= s.contains("skip");
                sliver |= s.contains("sliver");
                storm |= s.contains("storm");
                trample |= s.contains("trample");
                tutor |= s.contains("search your library") && !s.contains("basic land");
                tutorBasic |= s.contains("search your library") && s.contains("basic land");
                twiceAs |= s.contains("twice that many") || s.contains("twice as much");
                unblockable |= s.contains("can't be blocked");
                undying |= s.contains("undying");
                untapTarget |= s.contains("untap target");
                whenCounterThatSpell |= s.contains("when") && s.contains("counter that spell");
                wheneverEnters |= s.contains("when") && s.contains("another") && s.contains("enters");
                youControlTarget |= s.contains("you control target");
                yourOpponentsControl |= s.contains("your opponents control");
                whenYouCast |= s.contains("when you cast") || s.contains("whenever you cast");
            }

            for (ManaCost cost : card.getManaCost()) {
                if (cost.getText().contains("X")) {
                    xCost = true;
                }
            }
            for (Ability a : card.getAbilities()) {
                for (ManaCost cost : a.getManaCosts()) {
                    if (cost.getText().contains("X")) {
                        xCost = true;
                    }
                }
            }

            if (extraTurns) {
                thisMaxPower = Math.max(thisMaxPower, 7);
            }
            if (buyback) {
                thisMaxPower = Math.max(thisMaxPower, 6);
            }
            if (tutor) {
                thisMaxPower = Math.max(thisMaxPower, 6);
            }
            if (annihilator) {
                thisMaxPower = Math.max(thisMaxPower, 5);
            }
            if (cantUntap) {
                thisMaxPower = Math.max(thisMaxPower, 5);
            }
            if (costLessEach) {
                thisMaxPower = Math.max(thisMaxPower, 5);
            }
            if (infect) {
                thisMaxPower = Math.max(thisMaxPower, 5);
            }
            if (overload) {
                thisMaxPower = Math.max(thisMaxPower, 5);
            }
            if (twiceAs) {
                thisMaxPower = Math.max(thisMaxPower, 5);
            }
            if (cascade) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (doesntUntap) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (each) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (exileAll) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (flash) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (flashback) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (flicker) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (gainControl) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (lifeTotalBecomes) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (mayCastForFree) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (preventDamage) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (proliferate) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (protection) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (putUnderYourControl) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (returnFromYourGY) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (sacrifice) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (skip) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (storm) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (unblockable) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (whenCounterThatSpell) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (wheneverEnters) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (xCost) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (youControlTarget) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (yourOpponentsControl) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (whenYouCast) {
                thisMaxPower = Math.max(thisMaxPower, 4);
            }
            if (anyNumberOfTarget) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (createToken) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (destroyAll) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (dredge) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (hexproof) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (shroud) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (undying) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (persist) {
                thisMaxPower = Math.max(thisMaxPower, 3);
            }
            if (cantBe) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (evoke) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (exile) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (menace) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (miracle) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (sliver) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (untapTarget) {
                thisMaxPower = Math.max(thisMaxPower, 2);
            }
            if (copy) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }
            if (counter) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }
            if (destroy) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }
            if (drawCards) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }
            if (exalted) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }
            if (retrace) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }
            if (trample) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }
            if (tutorBasic) {
                thisMaxPower = Math.max(thisMaxPower, 1);
            }

            if (card.isPlaneswalker()) {
                if (card.getName().toLowerCase(Locale.ENGLISH).equals("jace, the mind sculptor")) {
                    thisMaxPower = Math.max(thisMaxPower, 6);
                }
                if (card.getName().toLowerCase(Locale.ENGLISH).equals("ugin, the spirit dragon")) {
                    thisMaxPower = Math.max(thisMaxPower, 5);
                }
                thisMaxPower = Math.max(thisMaxPower, 4);
            }

            String cn = card.getName().toLowerCase(Locale.ENGLISH);
            if (cn.equals("ancient tomb")
                    || cn.equals("anafenza, the foremost")
                    || cn.equals("arcum dagsson")
                    || cn.equals("armageddon")
                    || cn.equals("aura shards")
                    || cn.equals("azami, lady of scrolls")
                    || cn.equals("azusa, lost but seeking")
                    || cn.equals("back to basics")
                    || cn.equals("bane of progress")
                    || cn.equals("basalt monolith")
                    || cn.equals("blightsteel collossus")
                    || cn.equals("blood moon")
                    || cn.equals("braids, cabal minion")
                    || cn.equals("cabal coffers")
                    || cn.equals("captain sisay")
                    || cn.equals("celestial dawn")
                    || cn.equals("child of alara")
                    || cn.equals("coalition relic")
                    || cn.equals("craterhoof behemoth")
                    || cn.equals("deepglow skate")
                    || cn.equals("derevi, empyrial tactician")
                    || cn.equals("dig through time")
                    || cn.equals("edric, spymaster of trest")
                    || cn.equals("elesh norn, grand cenobite")
                    || cn.equals("entomb")
                    || cn.equals("force of will")
                    || cn.equals("food chain")
                    || cn.equals("gaddock teeg")
                    || cn.equals("gaea's cradle")
                    || cn.equals("grand arbiter augustin iv")
                    || cn.equals("grim monolith")
                    || cn.equals("hermit druid")
                    || cn.equals("hokori, dust drinker")
                    || cn.equals("humility")
                    || cn.equals("imperial seal")
                    || cn.equals("iona, shield of emeria")
                    || cn.equals("jin-gitaxias, core augur")
                    || cn.equals("karador, ghost chieftain")
                    || cn.equals("karakas")
                    || cn.equals("kataki, war's wage")
                    || cn.equals("knowledge pool")
                    || cn.equals("linvala, keeper of silence")
                    || cn.equals("living death")
                    || cn.equals("llawan, cephalid empress")
                    || cn.equals("loyal retainers")
                    || cn.equals("maelstrom wanderer")
                    || cn.equals("malfegor")
                    || cn.equals("master of cruelties")
                    || cn.equals("mana crypt")
                    || cn.equals("mana drain")
                    || cn.equals("mana vault")
                    || cn.equals("michiko konda, truth seeker")
                    || cn.equals("nath of the gilt-leaf")
                    || cn.equals("natural order")
                    || cn.equals("necrotic ooze")
                    || cn.equals("nicol bolas")
                    || cn.equals("numot, the devastator")
                    || cn.equals("oath of druids")
                    || cn.equals("pattern of rebirth")
                    || cn.equals("protean hulk")
                    || cn.equals("purphoros, god of the forge")
                    || cn.equals("ravages of war")
                    || cn.equals("reclamation sage")
                    || cn.equals("sen triplets")
                    || cn.equals("serra's sanctum")
                    || cn.equals("sheoldred, whispering one")
                    || cn.equals("sol ring")
                    || cn.equals("spore frog")
                    || cn.equals("stasis")
                    || cn.equals("strip mine")
                    || cn.equals("the tabernacle at pendrell vale")
                    || cn.equals("tinker")
                    || cn.equals("treasure cruise")
                    || cn.equals("urabrask the hidden")
                    || cn.equals("vorinclex, voice of hunger")
                    || cn.equals("winter orb")
                    || cn.equals("zur the enchanter")) {
                thisMaxPower = Math.max(thisMaxPower, 5);
            }

            // Parts of infinite combos
            if (cn.equals("animate artifact") || cn.equals("animar, soul of element")
                    || cn.equals("archaeomancer")
                    || cn.equals("ashnod's altar") || cn.equals("azami, lady of scrolls")
                    || cn.equals("aura flux")
                    || cn.equals("basalt monolith") || cn.equals("brago, king eternal")
                    || cn.equals("candelabra of tawnos") || cn.equals("cephalid aristocrat")
                    || cn.equals("cephalid illusionist") || cn.equals("changeling berserker")
                    || cn.equals("consecrated sphinx")
                    || cn.equals("cyclonic rift")
                    || cn.equals("the chain veil")
                    || cn.equals("cinderhaze wretch") || cn.equals("cryptic gateway")
                    || cn.equals("deadeye navigator") || cn.equals("derevi, empyrial tactician")
                    || cn.equals("doubling season") || cn.equals("dross scorpion")
                    || cn.equals("earthcraft") || cn.equals("erratic portal")
                    || cn.equals("enter the infinite") || cn.equals("omniscience")
                    || cn.equals("exquisite blood") || cn.equals("future sight")
                    || cn.equals("genesis chamber")
                    || cn.equals("ghave, guru of spores")
                    || cn.equals("grave pact")
                    || cn.equals("grave titan") || cn.equals("great whale")
                    || cn.equals("grim monolith") || cn.equals("gush")
                    || cn.equals("hellkite charger") || cn.equals("intruder alarm")
                    || cn.equals("helm of obedience")
                    || cn.equals("hermit druid")
                    || cn.equals("humility")
                    || cn.equals("iona, shield of emeria")
                    || cn.equals("karn, silver golem") || cn.equals("kiki-jiki, mirror breaker")
                    || cn.equals("krark-clan ironworks") || cn.equals("krenko, mob boss")
                    || cn.equals("krosan restorer") || cn.equals("laboratory maniac")
                    || cn.equals("leonin relic-warder") || cn.equals("leyline of the void")
                    || cn.equals("memnarch")
                    || cn.equals("meren of clan nel toth") || cn.equals("mikaeus, the unhallowed")
                    || cn.equals("mindcrank") || cn.equals("mindslaver")
                    || cn.equals("minion reflector") || cn.equals("mycosynth lattice")
                    || cn.equals("myr turbine") || cn.equals("narset, enlightened master")
                    || cn.equals("nekusar, the mindrazer") || cn.equals("norin the wary")
                    || cn.equals("notion thief")
                    || cn.equals("opalescence") || cn.equals("ornithopter")
                    || cn.equals("paradox engine")
                    || cn.equals("purphoros, god of the forge")
                    || cn.equals("peregrine drake") || cn.equals("palinchron")
                    || cn.equals("planar portal") || cn.equals("power artifact")
                    || cn.equals("rings of brighthearth") || cn.equals("rite of replication")
                    || cn.equals("sanguine bond") || cn.equals("sensei's divining top")
                    || cn.equals("splinter twin") || cn.equals("stony silence")
                    || cn.equals("sunder")
                    || cn.equals("storm cauldron") || cn.equals("teferi's puzzle box")
                    || cn.equals("tangle wire")
                    || cn.equals("teferi, mage of zhalfir")
                    || cn.equals("tezzeret the seeker") || cn.equals("time stretch")
                    || cn.equals("time warp") || cn.equals("training grounds")
                    || cn.equals("triskelavus") || cn.equals("triskelion")
                    || cn.equals("turnabout") || cn.equals("umbral mantle")
                    || cn.equals("uyo, silent prophet") || cn.equals("voltaic key")
                    || cn.equals("workhorse") || cn.equals("worldgorger dragon")
                    || cn.equals("worthy cause") || cn.equals("yawgmoth's will")
                    || cn.equals("zealous conscripts")) {
                thisMaxPower = Math.max(thisMaxPower, 12);
                numberInfinitePieces++;
            }
            edhPowerLevel += thisMaxPower;
        }

        ObjectColor color = null;
        for (Card commander : deck.getSideboard()) {
            int thisMaxPower = 0;
            String cn = commander.getName().toLowerCase(Locale.ENGLISH);
            if (color == null) {
                color = commander.getColor(null);
            } else {
                color = color.union(commander.getColor(null));
            }

            FilterMana commanderColor = commander.getColorIdentity();
            if (commanderColor.isWhite()) {
                color.setWhite(true);
            }
            if (commanderColor.isBlue()) {
                color.setBlue(true);
            }
            if (commanderColor.isBlack()) {
                color.setBlack(true);
            }
            if (commanderColor.isRed()) {
                color.setRed(true);
            }
            if (commanderColor.isGreen()) {
                color.setGreen(true);
            }

            // Least fun commanders
            if (cn.equals("animar, soul of element")
                    || cn.equals("azami, lady of scrolls")
                    || cn.equals("braids, cabal minion")
                    || cn.equals("child of alara")
                    || cn.equals("derevi, empyrial tactician")
                    || cn.equals("edric, spymaster of trest")
                    || cn.equals("gaddock teeg")
                    || cn.equals("grand arbiter augustin iv")
                    || cn.equals("hokori, dust drinker")
                    || cn.equals("iona, shield of emeria")
                    || cn.equals("jin-gitaxias, core augur")
                    || cn.equals("kaalia of the vast")
                    || cn.equals("karador, ghost chieftain")
                    || cn.equals("leovold, emissary of trest")
                    || cn.equals("linvala, keeper of silence")
                    || cn.equals("llawan, cephalid empress")
                    || cn.equals("memnarch")
                    || cn.equals("meren of clan nel toth")
                    || cn.equals("michiko konda, truth seeker")
                    || cn.equals("narset, enlightened master")
                    || cn.equals("nekusar, the mindrazer")
                    || cn.equals("norin the wary")
                    || cn.equals("numot, the devastator")
                    || cn.equals("sheoldred, whispering one")
                    || cn.equals("teferi, mage of zhalfir")
                    || cn.equals("zur the enchanter")) {
                thisMaxPower = Math.max(thisMaxPower, 25);
            }

            // Next least fun commanders
            if (cn.equals("anafenza, the foremost")
                    || cn.equals("arcum dagsson")
                    || cn.equals("azusa, lost but seeking")
                    || cn.equals("brago, king eternal")
                    || cn.equals("captain sisay")
                    || cn.equals("elesh norn, grand cenobite")
                    || cn.equals("malfegor")
                    || cn.equals("maelstrom wanderer")
                    || cn.equals("mikaeus the unhallowed")
                    || cn.equals("nath of the gilt-leaf")
                    || cn.equals("prossh, skyraider of kher")
                    || cn.equals("purphoros, god of the forge")
                    || cn.equals("sen triplets")
                    || cn.equals("urabrask the hidden")
                    || cn.equals("vorinclex, voice of hunger")) {
                thisMaxPower = Math.max(thisMaxPower, 15);
            }
            edhPowerLevel += thisMaxPower;
        }

        edhPowerLevel += numberInfinitePieces * 12;
        edhPowerLevel = Math.round(edhPowerLevel / 10);
        if (edhPowerLevel >= 100) {
            edhPowerLevel = 99;
        }
        if (color != null) {
            edhPowerLevel += (color.isWhite() ? 10000000 : 0);
            edhPowerLevel += (color.isBlue() ? 1000000 : 0);
            edhPowerLevel += (color.isBlack() ? 100000 : 0);
            edhPowerLevel += (color.isRed() ? 10000 : 0);
            edhPowerLevel += (color.isGreen() ? 1000 : 0);
        }
        return edhPowerLevel;
    }
}
