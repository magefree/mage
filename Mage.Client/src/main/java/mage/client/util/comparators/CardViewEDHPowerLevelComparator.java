package mage.client.util.comparators;

import mage.view.CardView;

import java.util.Locale;

/**
 * @author spjspj
 */
public class CardViewEDHPowerLevelComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        return Integer.compare(getPowerLevel(o1), getPowerLevel(o2));
    }

    @Override
    public String getCategoryName(CardView sample) {
        return "EDH: " + getPowerLevel(sample);
    }

    private int getPowerLevel(CardView card) {

        int thisMaxPower = 0;

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
            cantBe |= s.contains("can't be");
            cantUntap |= s.contains("can't untap") || s.contains("don't untap");
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
            thisMaxPower = Math.max(thisMaxPower, 4);
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
            thisMaxPower = Math.max(thisMaxPower, 6);
        }

        String cn = card.getName().toLowerCase(Locale.ENGLISH);
        if (cn.equals("acid rain")
                || cn.equals("agent of treachery")
                || cn.equals("anafenza, the foremost")
                || cn.equals("ancient tomb")
                || cn.equals("animar, soul of element")
                || cn.equals("animate artifact")
                || cn.equals("apocalypse")
                || cn.equals("archaeomancer")
                || cn.equals("arcum dagsson")
                || cn.equals("armageddon")
                || cn.equals("ashnod's altar")
                || cn.equals("atraxa, praetors' voice")
                || cn.equals("aura flux")
                || cn.equals("aura shards")
                || cn.equals("avacyn, angel of hope")
                || cn.equals("azami, lady of scrolls")
                || cn.equals("azusa, lost but seeking")
                || cn.equals("back to basics")
                || cn.equals("bane of progress")
                || cn.equals("basalt monolith")
                || cn.equals("bend or break")
                || cn.equals("blightsteel collossus")
                || cn.equals("blightsteel colossus")
                || cn.equals("blood moon")
                || cn.equals("boil")
                || cn.equals("boiling seas")
                || cn.equals("brago, king eternal")
                || cn.equals("braids, cabal minion")
                || cn.equals("bribery")
                || cn.equals("burning sands")
                || cn.equals("cabal coffers")
                || cn.equals("candelabra of tawnos")
                || cn.equals("captain sisay")
                || cn.equals("card view")
                || cn.equals("cataclysm")
                || cn.equals("catastrophe")
                || cn.equals("celestial dawn")
                || cn.equals("cephalid aristocrat")
                || cn.equals("cephalid illusionist")
                || cn.equals("changeling berserker")
                || cn.equals("child of alara")
                || cn.equals("chulane, teller of tales")
                || cn.equals("cinderhaze wretch")
                || cn.equals("coalition relic")
                || cn.equals("confusion in the ranks")
                || cn.equals("consecrated sphinx")
                || cn.equals("contamination")
                || cn.equals("craterhoof behemoth")
                || cn.equals("cryptic gateway")
                || cn.equals("cyclonic rift")
                || cn.equals("deadeye navigator")
                || cn.equals("death cloud")
                || cn.equals("decree of annihilation")
                || cn.equals("decree of silence")
                || cn.equals("deepglow skate")
                || cn.equals("demonic consultation")
                || cn.equals("derevi, empyrial tactician")
                || cn.equals("devastation")
                || cn.equals("dig through time")
                || cn.equals("divine intervention")
                || cn.equals("dockside extortionist")
                || cn.equals("doomsday")
                || cn.equals("doubling season")
                || cn.equals("drannith magistrate")
                || cn.equals("dross scorpion")
                || cn.equals("earthcraft")
                || cn.equals("edric, spymaster of trest")
                || cn.equals("elesh norn, grand cenobite")
                || cn.equals("embargo")
                || cn.equals("emrakul, the promised end")
                || cn.equals("enter the infinite")
                || cn.equals("entomb")
                || cn.equals("epicenter")
                || cn.equals("erratic portal")
                || cn.equals("expropriate")
                || cn.equals("exquisite blood")
                || cn.equals("fall of the thran")
                || cn.equals("fierce guardianship")
                || cn.equals("food chain")
                || cn.equals("force of negation")
                || cn.equals("force of will")
                || cn.equals("future sight")
                || cn.equals("gaddock teeg")
                || cn.equals("gaea's cradle")
                || cn.equals("genesis chamber")
                || cn.equals("ghave, guru of spores")
                || cn.equals("gilded drake")
                || cn.equals("glenn, the voice of calm")
                || cn.equals("global ruin")
                || cn.equals("golos, tireless pilgrim")
                || cn.equals("grand arbiter augustin iv")
                || cn.equals("grave pact")
                || cn.equals("grave titan")
                || cn.equals("great whale")
                || cn.equals("grim monolith")
                || cn.equals("grip of chaos")
                || cn.equals("gush")
                || cn.equals("hellkite charger")
                || cn.equals("hermit druid")
                || cn.equals("hokori, dust drinker")
                || cn.equals("humility")
                || cn.equals("impending disaster")
                || cn.equals("imperial seal")
                || cn.equals("intruder alarm")
                || cn.equals("invoke prejudice")
                || cn.equals("iona, shield of emeria")
                || cn.equals("jin-gitaxias, core augur")
                || cn.equals("jokulhaups")
                || cn.equals("kaalia of the vast")
                || cn.equals("karador, ghost chieftain")
                || cn.equals("karakas")
                || cn.equals("karn, silver golem")
                || cn.equals("kataki, war's wage")
                || cn.equals("keldon firebombers")
                || cn.equals("kiki-jiki, mirror breaker")
                || cn.equals("kinnan, bonder prodigy")
                || cn.equals("knowledge pool")
                || cn.equals("kozilek, butcher of truth")
                || cn.equals("krark-clan ironworks")
                || cn.equals("krenko, mob boss")
                || cn.equals("krosan restorer")
                || cn.equals("laboratory maniac")
                || cn.equals("land equilibrium")
                || cn.equals("leonin relic-warder")
                || cn.equals("leovold, emissary of trest")
                || cn.equals("leyline of the void")
                || cn.equals("linvala, keeper of silence")
                || cn.equals("living death")
                || cn.equals("llawan, cephalid empress")
                || cn.equals("loyal retainers")
                || cn.equals("maelstrom wanderer")
                || cn.equals("magister sphinx")
                || cn.equals("malfegor")
                || cn.equals("mana breach")
                || cn.equals("mana crypt")
                || cn.equals("mana drain")
                || cn.equals("mana vault")
                || cn.equals("mana vortex")
                || cn.equals("master of cruelties")
                || cn.equals("memnarch")
                || cn.equals("meren of clan nel toth")
                || cn.equals("michiko konda, truth seeker")
                || cn.equals("mikaeus the unhallowed")
                || cn.equals("mikaeus, the unhallowed")
                || cn.equals("mindcrank")
                || cn.equals("mindslaver")
                || cn.equals("minion reflector")
                || cn.equals("mycosynth lattice")
                || cn.equals("myr turbine")
                || cn.equals("narset, enlightened master")
                || cn.equals("narset, parter of veils")
                || cn.equals("nath of the gilt-leaf")
                || cn.equals("natural order")
                || cn.equals("necrotic ooze")
                || cn.equals("negan, the cold-blooded")
                || cn.equals("nekusar, the mindrazer")
                || cn.equals("nether void")
                || cn.equals("nexus of fate")
                || cn.equals("nicol bolas")
                || cn.equals("norin the wary")
                || cn.equals("notion thief")
                || cn.equals("numot, the devastator")
                || cn.equals("oath of druids")
                || cn.equals("obliterate")
                || cn.equals("oko, thief of crowns")
                || cn.equals("oloro, ageless ascetic")
                || cn.equals("omniscience")
                || cn.equals("opalescence")
                || cn.equals("opposition agent")
                || cn.equals("oppression")
                || cn.equals("ornithopter")
                || cn.equals("overwhelming splendor")
                || cn.equals("palinchron")
                || cn.equals("paradox engine")
                || cn.equals("pattern of rebirth")
                || cn.equals("peregrine drake")
                || cn.equals("planar portal")
                || cn.equals("possessed portal")
                || cn.equals("power artifact")
                || cn.equals("price of glory")
                || cn.equals("prossh, skyraider of kher")
                || cn.equals("protean hulk")
                || cn.equals("purphoros, god of the forge")
                || cn.equals("ravages of war")
                || cn.equals("reclamation sage")
                || cn.equals("rhystic study")
                || cn.equals("rick, steadfast leader")
                || cn.equals("rings of brighthearth")
                || cn.equals("rising waters")
                || cn.equals("rite of replication")
                || cn.equals("ruination")
                || cn.equals("sanguine bond")
                || cn.equals("scrambleverse")
                || cn.equals("seedborn muse")
                || cn.equals("sen triplets")
                || cn.equals("sensei's divining top")
                || cn.equals("serra's sanctum")
                || cn.equals("sheoldred, whispering one")
                || cn.equals("sire of insanity")
                || cn.equals("skithiryx, the blight dragon")
                || cn.equals("smokestack")
                || cn.equals("smothering tithe")
                || cn.equals("sol ring")
                || cn.equals("sorin markov")
                || cn.equals("splinter twin")
                || cn.equals("spore frog")
                || cn.equals("stasis")
                || cn.equals("static orb")
                || cn.equals("stony silence")
                || cn.equals("storage matrix")
                || cn.equals("storm cauldron")
                || cn.equals("strip mine")
                || cn.equals("sunder")
                || cn.equals("survival of the fittest")
                || cn.equals("table view")
                || cn.equals("tainted aether")
                || cn.equals("tangle wire")
                || cn.equals("tectonic break")
                || cn.equals("teferi's protection")
                || cn.equals("teferi's puzzle box")
                || cn.equals("teferi, mage of zhalfir")
                || cn.equals("teferi, master of time")
                || cn.equals("teferi, time raveler")
                || cn.equals("temporal manipulation")
                || cn.equals("tergrid, god of fright")
                || cn.equals("text view")
                || cn.equals("tezzeret the seeker")
                || cn.equals("thassa's oracle")
                || cn.equals("the chain veil")
                || cn.equals("the tabernacle at pendrell vale")
                || cn.equals("thieves' auction")
                || cn.equals("thoughts of ruin")
                || cn.equals("thrasios, triton hero")
                || cn.equals("time stretch")
                || cn.equals("time warp")
                || cn.equals("tinker")
                || cn.equals("tooth and nail")
                || cn.equals("torment of hailfire")
                || cn.equals("torpor orb")
                || cn.equals("training grounds")
                || cn.equals("treasure cruise")
                || cn.equals("triskelavus")
                || cn.equals("triskelion")
                || cn.equals("triumph of the hordes")
                || cn.equals("turnabout")
                || cn.equals("ugin, the spirit dragon")
                || cn.equals("ulamog, the ceaseless hunger")
                || cn.equals("ulamog, the infinite gyre")
                || cn.equals("umbral mantle")
                || cn.equals("urabrask the hidden")
                || cn.equals("urza, lord high artificer")
                || cn.equals("uyo, silent prophet")
                || cn.equals("void winnower")
                || cn.equals("voltaic key")
                || cn.equals("vorinclex, voice of hunger")
                || cn.equals("wake of destruction")
                || cn.equals("warp world")
                || cn.equals("winter orb")
                || cn.equals("workhorse")
                || cn.equals("worldgorger dragon")
                || cn.equals("worthy cause")
                || cn.equals("xanathar, guild kingpin")
                || cn.equals("yawgmoth's will")
                || cn.equals("zealous conscripts")
                || cn.equals("zur the enchanter")) {
                    thisMaxPower = Math.max(thisMaxPower, 12);
                }
        return thisMaxPower;
    }

}


