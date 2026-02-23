package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.Duration;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoaringFurnaceSteamingSauna extends RoomCard {

    public RoaringFurnaceSteamingSauna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{1}{R}", "{3}{U}{U}");

        // Roaring Furnace
        // When you unlock this door, this Room deals damage equal to the number of cards in your hand to target creature an opponent controls.
        Ability ability = new UnlockThisDoorTriggeredAbility(new DamageTargetEffect(CardsInControllerHandCount.ANY), false, true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Steaming Sauna
        // You have no maximum hand size.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // At the beginning of your end step, draw a card.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private RoaringFurnaceSteamingSauna(final RoaringFurnaceSteamingSauna card) {
        super(card);
    }

    @Override
    public RoaringFurnaceSteamingSauna copy() {
        return new RoaringFurnaceSteamingSauna(this);
    }
}
