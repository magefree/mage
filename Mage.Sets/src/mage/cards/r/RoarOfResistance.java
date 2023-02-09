package mage.cards.r;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Set;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RoarOfResistance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures attacking your opponents");

    static {
        filter.add(RoarOfResistancePredicate.instance);
    }

    public RoarOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Creature tokens you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));

        // Whenever one or more creatures attack, you may pay {1}{R}. If you do, creatures attacking
        // your opponents and/or planeswalkers they control get +2/+0 until end of turn.
        this.addAbility(new RoarOfResistanceTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(
                new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, false), new ManaCostsImpl<>("{1}{R}")
        )));
    }

    private RoarOfResistance(final RoarOfResistance card) {
        super(card);
    }

    @Override
    public RoarOfResistance copy() {
        return new RoarOfResistance(this);
    }
}

enum RoarOfResistancePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        UUID defenderId = game.getCombat().getDefenderId(input.getObject().getId());
        Set<UUID> opponents = game.getOpponents(input.getSource().getControllerId());
        Permanent attackedPlaneswalker = game.getPermanent(defenderId);
        if (attackedPlaneswalker != null
                && attackedPlaneswalker.isPlaneswalker(game)
                && opponents.contains(attackedPlaneswalker.getControllerId())) {
            return true;
        } else return opponents.contains(defenderId);
    }
}

class RoarOfResistanceTriggeredAbility extends TriggeredAbilityImpl {

    public RoarOfResistanceTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
    }

    public RoarOfResistanceTriggeredAbility(final RoarOfResistanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RoarOfResistanceTriggeredAbility copy() {
        return new RoarOfResistanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getCombat().getAttackers().isEmpty();
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack, you may pay {1}{R}. If you do, creatures attacking your opponents " +
                "and/or planeswalkers they control get +2/+0 until end of turn.";
    }
}
