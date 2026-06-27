package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessControlledEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class TheKingpinOfCrime extends CardImpl {

    private static final FilterCreaturePermanent filter
        = new FilterCreaturePermanent("creatures you control with toughness greater than their power");

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    public TheKingpinOfCrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Extort
        this.addAbility(new ExtortAbility());

        // Whenever you attack, you may pay 2 life. If you do, until end of turn, creatures you control with toughness
        // greater than their power assign combat damage equal to their toughness rather than their power.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new DoIfCostPaid(
            new CombatDamageByToughnessControlledEffect(filter, Duration.EndOfTurn)
                .setText("until end of turn, creatures you control with toughness greater than their power "
                    + "assign combat damage equal to their toughness rather than their power"),
            new PayLifeCost(2)
        ), 1);
        this.addAbility(ability);
    }

    private TheKingpinOfCrime(final TheKingpinOfCrime card) {
        super(card);
    }

    @Override
    public TheKingpinOfCrime copy() {
        return new TheKingpinOfCrime(this);
    }
}
