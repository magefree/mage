package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
                "{this} +3/+0 as long as there are ten or more cards in a single graveyard"
        )));

        // Swimmer in Nightmares can't be blocked as long as you control an Ashiok planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), condition,
                "{this} can't be blocked as long as you control an Ashiok planeswalker"
        )));
    }

    private SwimmerInNightmares(final SwimmerInNightmares card) {
        super(card);
    }

    @Override
    public SwimmerInNightmares copy() {
        return new SwimmerInNightmares(this);
    }
}

enum SwimmerInNightmaresCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .map(Player::getGraveyard)
                .map(HashSet::size)
                .anyMatch(x -> x >= 10);
    }
}