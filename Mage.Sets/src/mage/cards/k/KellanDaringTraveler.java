package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.MapToken;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class KellanDaringTraveler extends AdventureCard {

    private static final DynamicValue xValue = new IntPlusDynamicValue(1, KellanDynamicValue.instance);
    private static final Hint hint = new ValueHint("Number of opponents controlling an artifact", KellanDynamicValue.instance);

    public KellanDaringTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{W}", "Journey On", "{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Kellan, Daring Traveler attacks, reveal the top card of your library. If it's a creature card with mana value 3 or less, put it into your hand. Otherwise, you may put it into your graveyard.
        this.addAbility(new AttacksTriggeredAbility(new KellanDaringTravelerEffect()));

        // Journey On
        // Create X Map tokens, where X is one plus the number of opponents who control an artifact.
        this.getSpellCard().getSpellAbility().addEffect(
                new CreateTokenEffect(new MapToken(), xValue)
                        .setText("create X Map tokens, where X is one plus the number of opponents who control an artifact")
        );
        this.getSpellCard().getSpellAbility().addHint(hint);

        this.finalizeAdventure();
    }

    private KellanDaringTraveler(final KellanDaringTraveler card) {
        super(card);
    }

    @Override
    public KellanDaringTraveler copy() {
        return new KellanDaringTraveler(this);
    }
}

enum KellanDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Set<UUID> playerControllingArtifacts = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_ARTIFACT,
                        sourceAbility.getControllerId(),
                        sourceAbility, game
                ).stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toSet());
        return game.getOpponents(sourceAbility.getControllerId())
                .stream()
                .filter(p -> playerControllingArtifacts.contains(p))
                .mapToInt(p -> 1)
                .sum();
    }

    @Override
    public KellanDynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "X";
    }
}

class KellanDaringTravelerEffect extends OneShotEffect {

    public KellanDaringTravelerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. "
                + "If it's a creature card with mana value 3 or less, put it into your hand. "
                + "Otherwise, you may put it into your graveyard.";
    }

    private KellanDaringTravelerEffect(final KellanDaringTravelerEffect effect) {
        super(effect);
    }

    @Override
    public KellanDaringTravelerEffect copy() {
        return new KellanDaringTravelerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);

            if (card != null) {
                if (card.isCreature(game) && card.getManaValue() <= 3) {
                    controller.moveCards(card, Zone.HAND, source, game);
                } else if (controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " in your graveyard?", source, game)) {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }
}
