package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class DainOfTheAncientHalls extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DWARF, "Dwarves you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Dwarves you control", xValue);

    public DainOfTheAncientHalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Dain attacks, he deals damage equal to the number of Dwarves you control to each opponent.
        this.addAbility(new AttacksTriggeredAbility(
            new DamagePlayersEffect(Outcome.Benefit, xValue, TargetController.OPPONENT)
                .setText("he deals damage equal to the number of Dwarves you control to each opponent")
        ).addHint(hint));
    }

    private DainOfTheAncientHalls(final DainOfTheAncientHalls card) {
        super(card);
    }

    @Override
    public DainOfTheAncientHalls copy() {
        return new DainOfTheAncientHalls(this);
    }
}
