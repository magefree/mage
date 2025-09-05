package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class PassengerFerry extends CardImpl {

    static final FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PassengerFerry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever this Vehicle attacks, you may pay {U}. When you do, another target attacking creature can't be blocked this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new CantBeBlockedTargetEffect(), false
        );
        ability.addTarget(new TargetPermanent(filter));

        Ability triggeredAbility = new AttacksTriggeredAbility(
                new DoWhenCostPaid(ability, new ManaCostsImpl<>("{U}"),
                        "Make another attacking creature unblockable this turn?"), false);
        this.addAbility(triggeredAbility);

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private PassengerFerry(final PassengerFerry card) {
        super(card);
    }

    @Override
    public PassengerFerry copy() {
        return new PassengerFerry(this);
    }
}
