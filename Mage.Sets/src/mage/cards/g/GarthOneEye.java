package mage.cards.g;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GarthOneEye extends CardImpl {

    public GarthOneEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {T}: Choose a card name that hasn't been chosen from among Disenchant, Braingeyser, Terror, Shivan Dragon, Regrowth, and Black Lotus. Create a copy of the card with the chosen name. You may cast the copy.
        this.addAbility(new SimpleActivatedAbility(
                new GarthOneEyeEffect(), new TapSourceCost()
        ).addHint(GarthOneEyeHint.instance));
    }

    private GarthOneEye(final GarthOneEye card) {
        super(card);
    }

    @Override
    public GarthOneEye copy() {
        return new GarthOneEye(this);
    }
}

class GarthOneEyeEffect extends OneShotEffect {

    private static final List<String> names = Arrays.asList(
            "Disenchant", "Braingeyser", "Terror", "Shivan Dragon", "Regrowth", "Black Lotus"
    );
    private static final Map<String, Card> cardMap = new HashMap<>();

    private static final void initMap() {
        if (!cardMap.isEmpty()) {
            return;
        }
        cardMap.putAll(CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("LEA"))
                .stream()
                .filter(cardInfo -> names.contains(cardInfo.getName()))
                .collect(Collectors.toMap(CardInfo::getName, CardInfo::getCard)));
    }

    GarthOneEyeEffect() {
        super(Outcome.Benefit);
        staticText = "choose a card name that hasn't been chosen from among " +
                "Disenchant, Braingeyser, Terror, Shivan Dragon, Regrowth, and Black Lotus. " +
                "Create a copy of the card with the chosen name. You may cast the copy";
    }

    private GarthOneEyeEffect(final GarthOneEyeEffect effect) {
        super(effect);
    }

    @Override
    public GarthOneEyeEffect copy() {
        return new GarthOneEyeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        initMap();
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<String> alreadyChosen = getAlreadyChosen(game, source);
        Set<String> choices = new LinkedHashSet<>(names);
        choices.removeAll(alreadyChosen);
        String chosen;
        switch (choices.size()) {
            case 0:
                return false;
            case 1:
                chosen = choices.stream().findAny().orElse(null);
                break;
            default:
                Choice choice = new ChoiceImpl(true, ChoiceHintType.CARD);
                choice.setChoices(choices);
                player.choose(outcome, choice, game);
                chosen = choice.getChoice();
        }
        alreadyChosen.add(chosen);
        Card card = cardMap.get(chosen);
        if (card == null || !player.chooseUse(outcome, "Cast " + card.getName() + '?', source, game)) {
            return false;
        }
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        copiedCard.setZone(Zone.OUTSIDE, game);
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, false),
                game, false, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }

    private static final Set<String> getAlreadyChosen(Game game, Ability source) {
        String key = getKey(source);
        Object value = game.getState().getValue(key);
        if (value instanceof Set) {
            return (Set<String>) value;
        }
        Set<String> alreadyChosen = new HashSet<>();
        game.getState().setValue(key, alreadyChosen);
        return alreadyChosen;
    }

    static final String getKey(Ability source) {
        return source.getSourceId() + "_"
                + source.getSourceObjectZoneChangeCounter() + "_"
                + source.getOriginalId() + "_garth";
    }
}

enum GarthOneEyeHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (ability.getSourcePermanentIfItStillExists(game) == null) {
            return null;
        }
        Set<String> chosen = (Set<String>) game.getState().getValue(GarthOneEyeEffect.getKey(ability));
        if (chosen == null || chosen.isEmpty()) {
            return "Chosen names: None";
        }
        return "Chosen names: " + String.join(", ", chosen);
    }

    @Override
    public GarthOneEyeHint copy() {
        return instance;
    }
}
