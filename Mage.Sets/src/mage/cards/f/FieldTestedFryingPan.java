package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HalflingToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FieldTestedFryingPan extends CardImpl {

    public FieldTestedFryingPan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Field-Tested Frying Pan enters the battlefield, create a Food token, then create a 1/1 white Halfling creature token and attach Field-Tested Frying Pan to it.
        TriggeredAbility trigger = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken()));
        trigger.addEffect(new CreateTokenAttachSourceEffect(new HalflingToken(), " and").concatBy(", then"));
        this.addAbility(trigger);

        // Equipped creature has "Whenever you gain life, this creature gets +X/+X until end of turn, where X is the amount of life you gained."
        TriggeredAbility equippedTrigger = new GainLifeControllerTriggeredAbility(
                new BoostSourceEffect(
                        FieldTestedFryingPanValue.instance,
                        FieldTestedFryingPanValue.instance,
                        Duration.EndOfTurn
                ).setText("this creature gets +X/+X until end of turn, where X is the amount of life you gained.")
        );
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAttachedEffect(equippedTrigger, AttachmentType.EQUIPMENT)
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private FieldTestedFryingPan(final FieldTestedFryingPan card) {
        super(card);
    }

    @Override
    public FieldTestedFryingPan copy() {
        return new FieldTestedFryingPan(this);
    }
}

enum FieldTestedFryingPanValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("gainedLife");
    }

    @Override
    public FieldTestedFryingPanValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
