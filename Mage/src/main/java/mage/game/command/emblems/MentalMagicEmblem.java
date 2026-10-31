package mage.game.command.emblems;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.XmageToken;
import mage.players.Player;
import mage.watchers.common.MentalMagicNamesWatcher;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Mental Magic rules (https://mtg.fandom.com/wiki/Mental_Magic), given to every player of a Mental Magic game:
 * <p>
 * - any card in hand can be played face down as a land with "{T}: Add one mana of any color";
 * - any card in hand can be turned into a different card with exactly the same mana cost
 * (optionally it must also share a card type);
 * - each card name can be played only once per game, by any player (see MentalMagicNamesWatcher).
 * <p>
 * Playing a card as a land and turning it into another card are special actions of the cards in hand,
 * so players choose them by clicking a card.
 *
 * @author xico7
 */
public final class MentalMagicEmblem extends Emblem {

    public MentalMagicEmblem() {
        this(false);
    }

    /**
     * @param sameType new card must share a card type with the card in hand
     */
    public MentalMagicEmblem(boolean sameType) {
        super("Emblem Mental Magic");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new MentalMagicGainHandActionsEffect(sameType)));
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new MentalMagicCastOnceEffect()));
    }

    private MentalMagicEmblem(final MentalMagicEmblem card) {
        super(card);
    }

    @Override
    public MentalMagicEmblem copy() {
        return new MentalMagicEmblem(this);
    }

    @Override
    public void setSourceObjectAndInitImage(MageObject sourceObject) {
        // variant rules without a real emblem card, so use the xmage helper image
        this.sourceObject = sourceObject;
        TokenInfo foundInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_HELPER_EMBLEM, null);
        if (foundInfo != null) {
            this.setExpansionSetCode(foundInfo.getSetCode());
            this.setUsesVariousArt(false);
            this.setCardNumber("");
            this.setImageFileName(""); // use default
            this.setImageNumber(foundInfo.getImageNumber());
        } else {
            // how-to fix: add image to the tokens-database TokenRepository->loadXmageTokens
            throw new IllegalArgumentException("Wrong code usage: can't find xmage token info for: " + TokenRepository.XMAGE_IMAGE_NAME_HELPER_EMBLEM);
        }
    }
}

/**
 * Gives the Mental Magic actions to every card in its controller's hand
 */
class MentalMagicGainHandActionsEffect extends ContinuousEffectImpl {

    private final boolean sameType;

    MentalMagicGainHandActionsEffect(boolean sameType) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.sameType = sameType;
        staticText = "Each card in your hand has \"You may play this card face down as a Mental Land, "
                + "a land with '{T}: Add one mana of any color.' This counts as your land for the turn.\""
                + "<br>Each card with a mana cost in your hand has \"Choose a different card with exactly the same mana cost"
                + (sameType ? " that shares a card type with this card" : "")
                + " and whose name wasn't played or chosen this game. Exile this card face down"
                + " and put a card with the chosen name into your hand.\"";
    }

    private MentalMagicGainHandActionsEffect(final MentalMagicGainHandActionsEffect effect) {
        super(effect);
        this.sameType = effect.sameType;
    }

    @Override
    public MentalMagicGainHandActionsEffect copy() {
        return new MentalMagicGainHandActionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getHand().getCards(game)) {
            game.getState().addOtherAbility(card, new MentalMagicPlayLandAbility());
            if (!card.getManaCostSymbols().isEmpty()) {
                game.getState().addOtherAbility(card, new MentalMagicTransformAbility(sameType));
            }
        }
        return true;
    }
}

class MentalMagicPlayLandAbility extends SpecialAction {

    MentalMagicPlayLandAbility() {
        super(Zone.HAND);
        this.addEffect(new MentalMagicPlayLandEffect());
        this.setTiming(TimingRule.SORCERY);
    }

    private MentalMagicPlayLandAbility(final MentalMagicPlayLandAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player == null
                || !player.canPlayLand()
                || game.getState().getZone(getSourceId()) != Zone.HAND
                || game.getContinuousEffects().preventedByRuleModification(
                GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, getSourceId(), this, playerId), this, game, true)) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public MentalMagicPlayLandAbility copy() {
        return new MentalMagicPlayLandAbility(this);
    }
}

class MentalMagicPlayLandEffect extends OneShotEffect {

    MentalMagicPlayLandEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Play this card face down as a Mental Land (a land with \"{T}: Add one mana of any color.\")";
    }

    private MentalMagicPlayLandEffect(final MentalMagicPlayLandEffect effect) {
        super(effect);
    }

    @Override
    public MentalMagicPlayLandEffect copy() {
        return new MentalMagicPlayLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller == null || card == null
                || !controller.canPlayLand()
                || game.getState().getZone(card.getId()) != Zone.HAND
                || game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, card.getId(), source, controller.getId()))) {
            return false;
        }
        controller.moveCardsToExile(card, source, game, false, null, "");
        card.setFaceDown(true, game);

        Token land = new XmageToken("Mental Land").withAbility(new AnyColorManaAbility());
        land.addCardType(CardType.LAND);
        land.putOntoBattlefield(1, game, source, controller.getId());
        controller.incrementLandsPlayed();
        for (UUID landId : land.getLastAddedTokenIds()) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LAND_PLAYED, landId, source, controller.getId()));
        }
        game.informPlayers(controller.getLogName() + " plays a card face down as a Mental Land");
        return true;
    }
}

class MentalMagicTransformAbility extends SpecialAction {

    MentalMagicTransformAbility(boolean sameType) {
        super(Zone.HAND);
        this.addEffect(new MentalMagicTransformEffect(sameType));
    }

    private MentalMagicTransformAbility(final MentalMagicTransformAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (game.getState().getZone(getSourceId()) != Zone.HAND) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public MentalMagicTransformAbility copy() {
        return new MentalMagicTransformAbility(this);
    }
}

class MentalMagicTransformEffect extends OneShotEffect {

    private final boolean sameType;

    MentalMagicTransformEffect(boolean sameType) {
        super(Outcome.Benefit);
        this.sameType = sameType;
        staticText = "Turn this card into another card with exactly the same mana cost"
                + (sameType ? " and a shared card type" : "");
    }

    private MentalMagicTransformEffect(final MentalMagicTransformEffect effect) {
        super(effect);
        this.sameType = effect.sameType;
    }

    @Override
    public MentalMagicTransformEffect copy() {
        return new MentalMagicTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        // AI simulations would search the cards database on every copy of the game
        if (controller == null || card == null || game.isSimulation()
                || game.getState().getZone(card.getId()) != Zone.HAND) {
            return false;
        }
        MentalMagicNamesWatcher watcher = game.getState().getWatcher(MentalMagicNamesWatcher.class);

        String cost = String.join("", card.getManaCostSymbols());
        Collection<CardType> sharedTypes = sameType ? card.getCardType(game) : null;
        Map<String, MentalMagicCandidate> candidates = new LinkedHashMap<>();
        for (MentalMagicCandidate candidate : MentalMagicCardPool.findCandidates(card.getManaCostSymbols(), card.getManaValue(), sharedTypes)) {
            if (!candidate.getName().equals(card.getName())
                    && (watcher == null || !watcher.isTaken(candidate.getName()))) {
                candidates.put(candidate.getName(), candidate);
            }
        }
        if (candidates.isEmpty()) {
            game.informPlayer(controller, "Mental Magic: there are no unused cards with mana cost " + cost
                    + (sameType ? " that share a card type with " + card.getLogName() : ""));
            return false;
        }

        Choice choice = new ChoiceImpl(false, ChoiceHintType.CARD);
        choice.setMessage("Mental Magic: choose a card with mana cost " + cost);
        choice.setSubMessage(candidates.size() + " cards, best first ([Cube] - Vintage Cube, then by popularity)");
        for (MentalMagicCandidate candidate : candidates.values()) {
            choice.withItem(candidate.getName(), candidate.getLabel(), candidate.getSortScore(), ChoiceHintType.CARD, candidate.getName());
        }
        controller.choose(Outcome.Benefit, choice, game);
        MentalMagicCandidate chosen = choice.isChosen() ? candidates.get(choice.getChoiceKey()) : null;
        if (chosen == null) {
            return false;
        }
        Card newCard = chosen.getCardInfo().createCard();
        if (newCard == null) {
            return false;
        }

        Set<Card> newCards = new HashSet<>();
        newCards.add(newCard);
        game.loadCards(newCards, controller.getId());
        controller.moveCardsToExile(card, source, game, false, null, "");
        card.setFaceDown(true, game);
        controller.moveCards(newCard, Zone.HAND, source, game);
        if (watcher != null) {
            watcher.reserve(chosen.getName(), newCard.getId());
        }
        game.informPlayers(controller.getLogName() + " turns a card with mana cost " + cost + " into " + chosen.getName());
        return true;
    }
}

/**
 * Each card name can be cast only once per game
 */
class MentalMagicCastOnceEffect extends ContinuousRuleModifyingEffectImpl {

    MentalMagicCastOnceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each card name can be cast only once per game, by any player. "
                + "A name chosen for a card in hand can be cast only by that card.";
    }

    private MentalMagicCastOnceEffect(final MentalMagicCastOnceEffect effect) {
        super(effect);
    }

    @Override
    public MentalMagicCastOnceEffect copy() {
        return new MentalMagicCastOnceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        MentalMagicNamesWatcher watcher = game.getState().getWatcher(MentalMagicNamesWatcher.class);
        Card card = game.getCard(event.getSourceId());
        return watcher != null && card != null && !watcher.canCast(card.getName(), card.getMainCard().getId());
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Card card = game.getCard(event.getSourceId());
        return "Mental Magic: " + (card == null ? "that card" : card.getName()) + " was already played or chosen this game";
    }
}

class MentalMagicCandidate {

    private final CardInfo cardInfo;
    private final String label;
    private final int sortScore;

    MentalMagicCandidate(CardInfo cardInfo, int rank, boolean inCube) {
        this.cardInfo = cardInfo;
        this.sortScore = rank;
        String types = cardInfo.getTypes().stream().map(CardType::toString).collect(Collectors.joining(" "));
        this.label = cardInfo.getName() + " (" + types + ")" + (inCube ? " [Cube]" : "");
    }

    String getName() {
        return cardInfo.getName();
    }

    CardInfo getCardInfo() {
        return cardInfo;
    }

    String getLabel() {
        return label;
    }

    int getSortScore() {
        return sortScore;
    }
}

/**
 * Finds the cards a Mental Magic card can become, best first.
 * <p>
 * Ranking file: card names from best to worst, Vintage Cube cards first, then by EDHREC rank
 * (see Utils/mental-magic-ranking.py). Cards outside of the list go last, by name.
 */
class MentalMagicCardPool {

    private static final Logger logger = Logger.getLogger(MentalMagicCardPool.class);

    private static final String RANKING_RESOURCE = "/mentalmagic/card-ranking.txt";
    private static final String RANKING_CUBE_SECTION = "#cube";
    private static final String RANKING_POPULAR_SECTION = "#popular";

    private static final Set<CardType> CASTABLE_TYPES = EnumSet.of(
            CardType.ARTIFACT, CardType.BATTLE, CardType.CREATURE, CardType.ENCHANTMENT,
            CardType.INSTANT, CardType.PLANESWALKER, CardType.SORCERY
    );

    // mana value -> one printing per card name
    private static final Map<Integer, List<CardInfo>> cardsByManaValue = new ConcurrentHashMap<>();
    private static volatile Map<String, Integer> ranks; // normalized name -> position in the ranking
    private static volatile Set<String> cubeNames; // normalized names

    private MentalMagicCardPool() {
    }

    /**
     * @param manaCostSymbols mana cost of the card in hand, e.g. [{1}, {U}]
     * @param manaValue       mana value of the card in hand
     * @param sharedTypes     if not null, the cards must share one of these card types
     */
    static List<MentalMagicCandidate> findCandidates(List<String> manaCostSymbols, int manaValue, Collection<CardType> sharedTypes) {
        loadRanking();
        String cost = String.join("", manaCostSymbols);
        return cardsByManaValue.computeIfAbsent(manaValue, MentalMagicCardPool::loadCards)
                .stream()
                .filter(info -> cost.equals(String.join("", getMainManaCost(info))))
                .filter(info -> sharedTypes == null || sharesType(info, sharedTypes))
                .map(info -> {
                    String name = normalize(info.getName());
                    return new MentalMagicCandidate(info, ranks.getOrDefault(name, Integer.MAX_VALUE), cubeNames.contains(name));
                })
                .sorted(Comparator.comparingInt(MentalMagicCandidate::getSortScore).thenComparing(MentalMagicCandidate::getName))
                .collect(Collectors.toList());
    }

    private static boolean sharesType(CardInfo info, Collection<CardType> types) {
        return info.getTypes().stream().anyMatch(type -> type != CardType.KINDRED && types.contains(type));
    }

    private static List<String> getMainManaCost(CardInfo info) {
        // cards with adventure/omen spells store it as "spell part / main part"
        return info.getManaCosts(info.isCardWithSpellOption() ? CardInfo.ManaCostSide.RIGHT : CardInfo.ManaCostSide.LEFT);
    }

    private static List<CardInfo> loadCards(int manaValue) {
        Map<String, CardInfo> byName = new LinkedHashMap<>();
        for (CardInfo info : CardRepository.instance.findCards(new CardCriteria().manaValue(manaValue))) {
            if (!byName.containsKey(info.getName()) && isAllowed(info)) {
                byName.put(info.getName(), info);
            }
        }
        return new ArrayList<>(byName.values());
    }

    private static boolean isAllowed(CardInfo info) {
        if (info.getName().startsWith("A-") // alchemy rebalanced cards
                || info.isSplitCard() || info.isSplitCardHalf()
                || info.isNightCard() || info.isMeldCard() || info.isExtraDeckCard()) {
            return false;
        }
        List<CardType> types = info.getTypes();
        if (types.contains(CardType.LAND) || types.stream().noneMatch(CASTABLE_TYPES::contains)) {
            return false;
        }
        ExpansionSet set = Sets.findSet(info.getSetCode());
        return set != null && set.getSetType().isEternalLegal();
    }

    private static synchronized void loadRanking() {
        if (ranks != null) {
            return;
        }
        Map<String, Integer> newRanks = new HashMap<>();
        Set<String> newCubeNames = new HashSet<>();
        try (InputStream in = MentalMagicCardPool.class.getResourceAsStream(RANKING_RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("Can't find resource " + RANKING_RESOURCE);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            boolean cubeSection = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(RANKING_CUBE_SECTION) || line.equals(RANKING_POPULAR_SECTION)) {
                    cubeSection = line.equals(RANKING_CUBE_SECTION);
                } else if (!line.isEmpty() && !line.startsWith("#")) {
                    String name = normalize(line);
                    newRanks.putIfAbsent(name, newRanks.size());
                    if (cubeSection) {
                        newCubeNames.add(name);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Mental Magic: can't load card ranking, cards will be sorted by name", e);
        }
        cubeNames = newCubeNames;
        ranks = newRanks;
    }

    private static String normalize(String name) {
        return Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ENGLISH);
    }
}
