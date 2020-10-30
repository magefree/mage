package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwimmerInNightmares extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent(SubType.ASHIOK);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public SwimmerInNightmares(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Swimmer in Nightmares gets +3/+0 as long as there are ten or more cards in a single graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                SwimmerInNightmaresCondition.instance,
                "{this} gets +3/+0 as long as there are ten or more cards in a single graveyard"
        )).addHint(new ValueHint("Max cards in single graveyard", SwimmerInNightmaresCardsInSingleGraveyardValue.instance)));

        // Swimmer in Nightmares can't be blocked as long as you control an Ashiok planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), condition,
                "{this} can't be blocked as long as you control an Ashiok planeswalker"
        )).addHint(new ConditionHint(condition, "You control an Ashiok planeswalker")));
    }

    private SwimmerInNightmares(final SwimmerInNightmares card) {
        super(card);
    }

    @Override
    public SwimmerInNightmares copy() {
        return new SwimmerInNightmares(this);
    }
}

enum SwimmerInNightmaresCardsInSingleGraveyardValue implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getState()
                .getPlayersInRange(sourceAbility.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .map(Player::getGraveyard)
                .mapToInt(HashSet::size)
                .max()
                .orElse(0);
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum SwimmerInNightmaresCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return SwimmerInNightmaresCardsInSingleGraveyardValue.instance.calculate(game, source, null) >= 10;
    }
}