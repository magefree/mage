package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrcishSquatters extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public OrcishSquatters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ORC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Orcish Squatters attacks and isn't blocked, you may gain control of target land defending player controls for as long as you control Orcish Squatters. If you do, Orcish Squatters assigns no combat damage this turn.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new GainControlTargetEffect(Duration.WhileControlled), true);
        ability.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OrcishSquatters(final OrcishSquatters card) {
        super(card);
    }

    @Override
    public OrcishSquatters copy() {
        return new OrcishSquatters(this);
    }
}
