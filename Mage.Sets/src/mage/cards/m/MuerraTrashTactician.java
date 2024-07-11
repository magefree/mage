package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MuerraTrashTactician extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.RACCOON, "Raccoon you control");

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Raccoon you control", xValue);


    public MuerraTrashTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your first main phase, add {R} or {G} for each Raccoon you control.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new AddManaInAnyCombinationEffect(xValue, xValue, ColoredManaSymbol.R, ColoredManaSymbol.G)
                        .setText("add {R} or {G} for each Raccoon you control"),
                TargetController.YOU, false
        ).setTriggerPhrase("At the beginning of your first main phase, ").addHint(hint));

        // Whenever you expend 4, you gain 3 life.
        this.addAbility(new ExpendTriggeredAbility(
                new GainLifeEffect(3),
                ExpendTriggeredAbility.Expend.FOUR
        ));

        // Whenever you expend 8, exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.addAbility(new ExpendTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn),
                ExpendTriggeredAbility.Expend.EIGHT
        ));

    }

    private MuerraTrashTactician(final MuerraTrashTactician card) {
        super(card);
    }

    @Override
    public MuerraTrashTactician copy() {
        return new MuerraTrashTactician(this);
    }
}
