package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.TargetController;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlassworksShatteredYard extends RoomCard {
    public GlassworksShatteredYard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{R}", "{4}{R}");

        // Glassworks
        // When you unlock this door, this Room deals 4 damage to target creature an opponent controls.
        Ability ability = new UnlockThisDoorTriggeredAbility(new DamageTargetEffect(4), false, true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Shattered Yard
        // At the beginning of your end step, this Room deals 1 damage to each opponent.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT)));
    }

    private GlassworksShatteredYard(final GlassworksShatteredYard card) {
        super(card);
    }

    @Override
    public GlassworksShatteredYard copy() {
        return new GlassworksShatteredYard(this);
    }
}
