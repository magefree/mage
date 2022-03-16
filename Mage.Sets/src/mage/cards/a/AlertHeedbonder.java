package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlertHeedbonder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with vigilance");

    static {
        filter.add(new AbilityPredicate(VigilanceAbility.class));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public AlertHeedbonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/W}{G/W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, you gain 1 life for each creature you control with vigilance.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new GainLifeEffect(xValue)
                        .setText("you gain 1 life for each creature you control with vigilance"),
                TargetController.YOU, false
        ));
    }

    private AlertHeedbonder(final AlertHeedbonder card) {
        super(card);
    }

    @Override
    public AlertHeedbonder copy() {
        return new AlertHeedbonder(this);
    }
}
