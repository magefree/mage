package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Skizzik extends CardImpl {

    private static final Condition condition = new InvertCondition(KickedCondition.ONCE, "{this} wasn't kicked");

    public Skizzik(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of the end step, if Skizzik wasn't kicked, sacrifice it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect().setText("sacrifice it"), false, condition
        ));
    }

    private Skizzik(final Skizzik card) {
        super(card);
    }

    @Override
    public Skizzik copy() {
        return new Skizzik(this);
    }
}
