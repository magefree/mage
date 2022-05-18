package mage.deck;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.CommanderChooseColorAbility;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterMana;
import mage.util.CardUtil;
import mage.util.ManaUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public abstract class AbstractCommander extends Constructed {

    protected final List<String> bannedCommander = new ArrayList<>();
    protected final List<String> bannedPartner = new ArrayList<>();
    protected boolean partnerAllowed = true;

    public AbstractCommander(String name) {
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

    protected abstract boolean checkBanned(Map<String, Integer> counts);

    protected boolean checkCommander(Card commander, Set<Card> commanders) {
        return commander.hasCardTypeForDeckbuilding(CardType.CREATURE) && commander.isLegendary()
                || commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance())
                || commander.hasSubTypeForDeckbuilding(SubType.BACKGROUND) && commanders.size() == 2;
    }

    protected boolean checkPartners(Set<Card> commanders) {
        switch (commanders.size()) {
            case 1:
                return true;
            case 2:
                break;
            default:
                return false;
        }
        Iterator<Card> iter = commanders.iterator();
        Card commander1 = iter.next();
        Card commander2 = iter.next();
        if (commander1.getAbilities().containsClass(PartnerAbility.class)
                && commander2.getAbilities().containsClass(PartnerAbility.class)
                || commander1.getAbilities().containsClass(FriendsForeverAbility.class)
                && commander2.getAbilities().containsClass(FriendsForeverAbility.class)) {
            return true;
        }
        if (commander1.getAbilities().containsClass(PartnerWithAbility.class)
                && commander2.getAbilities().containsClass(PartnerWithAbility.class)) {
            String name1 = CardUtil.castStream(commander2.getAbilities().stream(), PartnerWithAbility.class).map(PartnerWithAbility::getPartnerName).findFirst().orElse(null);
            String name2 = CardUtil.castStream(commander1.getAbilities().stream(), PartnerWithAbility.class).map(PartnerWithAbility::getPartnerName).findFirst().orElse(null);
            if (commander1.getName().equals(name1) && commander2.getName().equals(name2)) {
                return true;
            }
            addError(DeckValidatorErrorType.PRIMARY, commander1.getName(), "Commander with invalid Partner (" + commander1.getName() + ')', true);
            addError(DeckValidatorErrorType.PRIMARY, commander2.getName(), "Commander with invalid Partner (" + commander2.getName() + ')', true);
            return false;
        }
        if (commander1.getAbilities().containsClass(ChooseABackgroundAbility.class) == commander2.hasSubTypeForDeckbuilding(SubType.BACKGROUND)
                || commander2.getAbilities().containsClass(ChooseABackgroundAbility.class) == commander1.hasSubTypeForDeckbuilding(SubType.BACKGROUND)) {
            return true;
        }
        if (commander1.hasSubTypeForDeckbuilding(SubType.BACKGROUND)) {
            addError(DeckValidatorErrorType.PRIMARY, commander1.getName(), "Background without valid Commander (" + commander1.getName() + ')', true);
        }
        if (commander2.hasSubTypeForDeckbuilding(SubType.BACKGROUND)) {
            addError(DeckValidatorErrorType.PRIMARY, commander1.getName(), "Background without valid Commander (" + commander2.getName() + ')', true);
        }
        return false;
    }

    private boolean checkColorIdentity(Deck deck, FilterMana colorIdentity, Set<Card> commanders) {
        int piperCount = commanders
                .stream()
                .filter(CommanderChooseColorAbility::checkCard)
                .mapToInt(x -> 1)
                .sum();
        if (piperCount == 0) {
            boolean valid = true;
            for (Card card : deck.getCards()) {
                if (!ManaUtil.isColorIdentityCompatible(colorIdentity, card.getColorIdentity())) {
                    addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid color (" + colorIdentity.toString() + ')', true);
                    valid = false;
                }
            }
            for (Card card : deck.getSideboard()) {
                if (!ManaUtil.isColorIdentityCompatible(colorIdentity, card.getColorIdentity())) {
                    addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid color (" + colorIdentity.toString() + ')', true);
                    valid = false;
                }
            }
            return valid;
        }
        FilterMana filterMana = new FilterMana();
        Stream.concat(
                deck.getCards().stream(),
                deck.getSideboard().stream()
        ).map(Card::getColorIdentity).forEach(filterMana::addAll);
        if (colorIdentity.getColorCount() + piperCount >= filterMana.getColorCount()) {
            return true;
        }
        StringBuilder sb = new StringBuilder()
                .append("Invalid color, commander color identity has ")
                .append(colorIdentity.getColorCount())
                .append(" color")
                .append(colorIdentity.getColorCount() > 1 ? "s" : "")
                .append(", plus ")
                .append(piperCount)
                .append(" cop")
                .append(piperCount > 1 ? "ies" : "y")
                .append(" of The Prismatic Piper, but the total amount of colors in the deck is ")
                .append(filterMana.getColorCount());
        addError(DeckValidatorErrorType.OTHER, "The Prismatic Piper", sb.toString());
        return false;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();
        FilterMana colorIdentity = new FilterMana();
        Set<Card> commanders = new HashSet<>();
        Card companion;

        int sbsize = deck.getSideboard().size();
        Card card1;
        Card card2;
        Card card3;
        Iterator<Card> iter;
        switch (deck.getSideboard().size()) {
            case 1:
                companion = null;
                commanders.add(deck.getSideboard().iterator().next());
                break;
            case 2:
                iter = deck.getSideboard().iterator();
                card1 = iter.next();
                card2 = iter.next();
                if (card1.getAbilities().stream().anyMatch(CompanionAbility.class::isInstance)) {
                    companion = card1;
                    commanders.add(card2);
                } else if (card2.getAbilities().stream().anyMatch(CompanionAbility.class::isInstance)) {
                    companion = card2;
                    commanders.add(card1);
                } else {
                    companion = null;
                    commanders.add(card1);
                    commanders.add(card2);
                }
                break;
            case 3:
                iter = deck.getSideboard().iterator();
                card1 = iter.next();
                card2 = iter.next();
                card3 = iter.next();
                if (card1.getAbilities().stream().anyMatch(CompanionAbility.class::isInstance)) {
                    companion = card1;
                    commanders.add(card2);
                    commanders.add(card3);
                } else if (card2.getAbilities().stream().anyMatch(CompanionAbility.class::isInstance)) {
                    companion = card2;
                    commanders.add(card1);
                    commanders.add(card3);
                } else if (card3.getAbilities().stream().anyMatch(CompanionAbility.class::isInstance)) {
                    companion = card3;
                    commanders.add(card1);
                    commanders.add(card2);
                } else {
                    companion = null;
                    addError(DeckValidatorErrorType.PRIMARY, "Commander", "Sideboard must contain only the commander(s) and up to 1 companion");
                    valid = false;
                }
                break;
            default:
                companion = null;
                addError(DeckValidatorErrorType.PRIMARY, "Commander", "Sideboard must contain only the commander(s) and up to 1 companion");
                valid = false;
        }

        if (companion != null && deck.getCards().size() + deck.getSideboard().size() != 101) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + 101 + " cards (companion doesn't count for deck size): has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        } else if (companion == null && deck.getCards().size() + deck.getSideboard().size() != 100) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + 100 + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(1, counts) && valid;
        valid = checkBanned(counts) && valid;
        valid = checkPartners(commanders) && valid;

        for (Card commander : commanders) {
            if (bannedCommander.contains(commander.getName())) {
                addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "Commander banned (" + commander.getName() + ')', true);
                valid = false;
            }
            if (!checkCommander(commander, commanders)) {
                addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "Commander invalid (" + commander.getName() + ')', true);
                valid = false;
            }
            if (commanders.size() == 2 && bannedPartner.contains(commander.getName())) {
                addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "Commander Partner banned (" + commander.getName() + ')', true);
                valid = false;
            }
            ManaUtil.collectColorIdentity(colorIdentity, commander.getColorIdentity());
        }

        // no needs in cards check on wrong commanders
        if (!valid) {
            return false;
        }

        valid = checkColorIdentity(deck, colorIdentity, commanders);

        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Not allowed Set: " + card.getExpansionSetCode(), true);
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Not allowed Set: " + card.getExpansionSetCode(), true);
                    valid = false;
                }
            }
        }
        // Check for companion legality
        if (companion != null) {
            Set<Card> cards = new HashSet<>(deck.getCards());
            cards.addAll(commanders);
            CompanionAbility companionAbility = CardUtil.castStream(
                    companion.getAbilities().stream(),
                    CompanionAbility.class
            ).findFirst().orElse(null);
            if (companionAbility == null || !companionAbility.isLegal(cards, getDeckMinSize())) {
                addError(DeckValidatorErrorType.PRIMARY, companion.getName(),
                        String.format("Commander companion illegal: %s", companionAbility.getLegalRule()), true);
                valid = false;
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

            for (String s : card.getManaCostSymbols()) {
                if (s.contains("X")) {
                    xCost = true;
                }
            }
            for (Ability a : card.getAbilities()) {
                for (String s : a.getManaCostSymbols()) {
                    if (s.contains("X")) {
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
                thisMaxPower = Math.max(thisMaxPower, 6);
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
                thisMaxPower = Math.max(thisMaxPower, 12);
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
                thisMaxPower = Math.max(thisMaxPower, 15);
                numberInfinitePieces++;
            }

            // Saltiest cards (edhrec)
            if (cn.equals("acid rain")
                    || cn.equals("agent of treachery")
                    || cn.equals("apocalypse")
                    || cn.equals("armageddon")
                    || cn.equals("atraxa, praetors' voice")
                    || cn.equals("aura shards")
                    || cn.equals("avacyn, angel of hope")
                    || cn.equals("back to basics")
                    || cn.equals("bend or break")
                    || cn.equals("blightsteel colossus")
                    || cn.equals("blood moon")
                    || cn.equals("boil")
                    || cn.equals("boiling seas")
                    || cn.equals("bribery")
                    || cn.equals("burning sands")
                    || cn.equals("card view")
                    || cn.equals("cataclysm")
                    || cn.equals("catastrophe")
                    || cn.equals("chulane, teller of tales")
                    || cn.equals("confusion in the ranks")
                    || cn.equals("consecrated sphinx")
                    || cn.equals("contamination")
                    || cn.equals("craterhoof behemoth")
                    || cn.equals("cyclonic rift")
                    || cn.equals("death cloud")
                    || cn.equals("decree of annihilation")
                    || cn.equals("decree of silence")
                    || cn.equals("demonic consultation")
                    || cn.equals("derevi, empyrial tactician")
                    || cn.equals("devastation")
                    || cn.equals("divine intervention")
                    || cn.equals("dockside extortionist")
                    || cn.equals("doomsday")
                    || cn.equals("doubling season")
                    || cn.equals("drannith magistrate")
                    || cn.equals("elesh norn, grand cenobite")
                    || cn.equals("embargo")
                    || cn.equals("emrakul, the promised end")
                    || cn.equals("epicenter")
                    || cn.equals("expropriate")
                    || cn.equals("fall of the thran")
                    || cn.equals("fierce guardianship")
                    || cn.equals("food chain")
                    || cn.equals("force of negation")
                    || cn.equals("force of will")
                    || cn.equals("gaddock teeg")
                    || cn.equals("gaea's cradle")
                    || cn.equals("gilded drake")
                    || cn.equals("glenn, the voice of calm")
                    || cn.equals("global ruin")
                    || cn.equals("golos, tireless pilgrim")
                    || cn.equals("grand arbiter augustin iv")
                    || cn.equals("grip of chaos")
                    || cn.equals("hokori, dust drinker")
                    || cn.equals("humility")
                    || cn.equals("impending disaster")
                    || cn.equals("invoke prejudice")
                    || cn.equals("iona, shield of emeria")
                    || cn.equals("jin-gitaxias, core augur")
                    || cn.equals("jokulhaups")
                    || cn.equals("keldon firebombers")
                    || cn.equals("kinnan, bonder prodigy")
                    || cn.equals("kozilek, butcher of truth")
                    || cn.equals("land equilibrium")
                    || cn.equals("linvala, keeper of silence")
                    || cn.equals("magister sphinx")
                    || cn.equals("mana breach")
                    || cn.equals("mana crypt")
                    || cn.equals("mana drain")
                    || cn.equals("mana vortex")
                    || cn.equals("mindslaver")
                    || cn.equals("narset, enlightened master")
                    || cn.equals("narset, parter of veils")
                    || cn.equals("negan, the cold-blooded")
                    || cn.equals("nether void")
                    || cn.equals("nexus of fate")
                    || cn.equals("notion thief")
                    || cn.equals("obliterate")
                    || cn.equals("oko, thief of crowns")
                    || cn.equals("oloro, ageless ascetic")
                    || cn.equals("omniscience")
                    || cn.equals("opposition agent")
                    || cn.equals("oppression")
                    || cn.equals("overwhelming splendor")
                    || cn.equals("palinchron")
                    || cn.equals("paradox engine")
                    || cn.equals("possessed portal")
                    || cn.equals("price of glory")
                    || cn.equals("protean hulk")
                    || cn.equals("ravages of war")
                    || cn.equals("rhystic study")
                    || cn.equals("rick, steadfast leader")
                    || cn.equals("rising waters")
                    || cn.equals("ruination")
                    || cn.equals("scrambleverse")
                    || cn.equals("seedborn muse")
                    || cn.equals("sen triplets")
                    || cn.equals("sire of insanity")
                    || cn.equals("skithiryx, the blight dragon")
                    || cn.equals("smokestack")
                    || cn.equals("smothering tithe")
                    || cn.equals("sorin markov")
                    || cn.equals("stasis")
                    || cn.equals("static orb")
                    || cn.equals("storage matrix")
                    || cn.equals("sunder")
                    || cn.equals("survival of the fittest")
                    || cn.equals("table view")
                    || cn.equals("tainted aether")
                    || cn.equals("tectonic break")
                    || cn.equals("teferi's protection")
                    || cn.equals("teferi, master of time")
                    || cn.equals("teferi, time raveler")
                    || cn.equals("temporal manipulation")
                    || cn.equals("tergrid, god of fright")
                    || cn.equals("text view")
                    || cn.equals("thassa's oracle")
                    || cn.equals("the tabernacle at pendrell vale")
                    || cn.equals("thieves' auction")
                    || cn.equals("thoughts of ruin")
                    || cn.equals("thrasios, triton hero")
                    || cn.equals("time stretch")
                    || cn.equals("time warp")
                    || cn.equals("tooth and nail")
                    || cn.equals("torment of hailfire")
                    || cn.equals("torpor orb")
                    || cn.equals("triumph of the hordes")
                    || cn.equals("ugin, the spirit dragon")
                    || cn.equals("ulamog, the ceaseless hunger")
                    || cn.equals("ulamog, the infinite gyre")
                    || cn.equals("urza, lord high artificer")
                    || cn.equals("void winnower")
                    || cn.equals("vorinclex, voice of hunger")
                    || cn.equals("wake of destruction")
                    || cn.equals("warp world")
                    || cn.equals("winter orb")
                    || cn.equals("xanathar, guild kingpin")
                    || cn.equals("zur the enchanter")) {
                thisMaxPower = Math.max(thisMaxPower, 15);
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
                    || cn.equals("anafenza, the foremost")
                    || cn.equals("arcum dagsson")
                    || cn.equals("azami, lady of scrolls")
                    || cn.equals("azusa, lost but seeking")
                    || cn.equals("brago, king eternal")
                    || cn.equals("braids, cabal minion")
                    || cn.equals("captain sisay")
                    || cn.equals("child of alara")
                    || cn.equals("derevi, empyrial tactician")
                    || cn.equals("edric, spymaster of trest")
                    || cn.equals("elesh norn, grand cenobite")
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
                    || cn.equals("maelstrom wanderer")
                    || cn.equals("malfegor")
                    || cn.equals("memnarch")
                    || cn.equals("meren of clan nel toth")
                    || cn.equals("michiko konda, truth seeker")
                    || cn.equals("mikaeus the unhallowed")
                    || cn.equals("narset, enlightened master")
                    || cn.equals("nath of the gilt-leaf")
                    || cn.equals("nekusar, the mindrazer")
                    || cn.equals("norin the wary")
                    || cn.equals("numot, the devastator")
                    || cn.equals("prossh, skyraider of kher")
                    || cn.equals("purphoros, god of the forge")
                    || cn.equals("sen triplets")
                    || cn.equals("sheoldred, whispering one")
                    || cn.equals("teferi, mage of zhalfir")
                    || cn.equals("urabrask the hidden")
                    || cn.equals("vorinclex, voice of hunger")
                    || cn.equals("zur the enchanter")) {
                thisMaxPower = Math.max(thisMaxPower, 25);
            }

            // Saltiest commanders
            if (cn.equals("atraxa, praetors' voice")
                    || cn.equals("avacyn, angel of hope")
                    || cn.equals("chulane, teller of tales")
                    || cn.equals("derevi, empyrial tactician")
                    || cn.equals("elesh norn, grand cenobite")
                    || cn.equals("emrakul, the promised end")
                    || cn.equals("gaddock teeg")
                    || cn.equals("glenn, the voice of calm")
                    || cn.equals("golos, tireless pilgrim")
                    || cn.equals("grand arbiter augustin iv")
                    || cn.equals("hokori, dust drinker")
                    || cn.equals("iona, shield of emeria")
                    || cn.equals("jin-gitaxias, core augur")
                    || cn.equals("kinnan, bonder prodigy")
                    || cn.equals("kozilek, butcher of truth")
                    || cn.equals("linvala, keeper of silence")
                    || cn.equals("narset, enlightened master")
                    || cn.equals("negan, the cold-blooded")
                    || cn.equals("oko, thief of crowns")
                    || cn.equals("oloro, ageless ascetic")
                    || cn.equals("rick, steadfast leader")
                    || cn.equals("sen triplets")
                    || cn.equals("skithiryx, the blight dragon")
                    || cn.equals("teferi, master of time")
                    || cn.equals("teferi, time raveler")
                    || cn.equals("thrasios, triton hero")
                    || cn.equals("ulamog, the ceaseless hunger")
                    || cn.equals("ulamog, the infinite gyre")
                    || cn.equals("urza, lord high artificer")
                    || cn.equals("vorinclex, voice of hunger")
                    || cn.equals("xanathar, guild kingpin")
                    || cn.equals("zur the enchanter")) {
                thisMaxPower = Math.max(thisMaxPower, 20);
            }
            edhPowerLevel += thisMaxPower;
        }

        edhPowerLevel += numberInfinitePieces * 18;
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
