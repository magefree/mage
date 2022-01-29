package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurgehackerMech extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker and opponent controls");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.VEHICLE);

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2, 2);
    private static final Hint hint = new ValueHint(
            "Vehicles you control", new PermanentsOnBattlefieldCount(filter2)
    );

    public SurgehackerMech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Surgehacker Mech enters the battlefield, it deals damage equal to twice the number of Vehicles you control to target creature or planeswalker an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.addHint(hint));

        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private SurgehackerMech(final SurgehackerMech card) {
        super(card);
    }

    @Override
    public SurgehackerMech copy() {
        return new SurgehackerMech(this);
    }
}
