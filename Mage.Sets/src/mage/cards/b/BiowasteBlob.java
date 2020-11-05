package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiowasteBlob extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.OOZE, "Oozes");

    public BiowasteBlob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Oozes you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));

        // At the beginning of your upkeep, if you control a commander, create a token that's a copy of Biowaste Blob.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new CreateTokenCopySourceEffect(), TargetController.YOU, false
                ), ControlACommanderCondition.instance, "At the beginning of your upkeep, " +
                "if you control a commander, create a token that's a copy of {this}."
        ));
    }

    private BiowasteBlob(final BiowasteBlob card) {
        super(card);
    }

    @Override
    public BiowasteBlob copy() {
        return new BiowasteBlob(this);
    }
}
