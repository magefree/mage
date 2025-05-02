package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Jmlundeen
 */
public final class AlacrianArmory extends CardImpl {

    private static final FilterCreaturePermanent creatureFilter = new FilterCreaturePermanent("Creatures you control");
    private static final FilterPermanent mountOrVehicleFilter = new FilterPermanent("Mount or Vehicle you control");

    static {
        mountOrVehicleFilter.add(Predicates.or(
            SubType.MOUNT.getPredicate(),
            SubType.VEHICLE.getPredicate()
        ));
        mountOrVehicleFilter.add(TargetController.YOU.getControllerPredicate());
    }
    public AlacrianArmory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        // Creatures you control get +0/+1 and have vigilance.
        Ability staticAbility = new SimpleStaticAbility(new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, creatureFilter));
        Effect effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, creatureFilter);
        effect.setText("and have vigilance");
        staticAbility.addEffect(effect);
        this.addAbility(staticAbility);
        // At the beginning of combat on your turn, choose up to one target Mount or Vehicle you control. Until end of turn, that permanent becomes saddled if it's a Mount and becomes an artifact creature if it's a Vehicle.
        Ability animateAbility = new BeginningOfCombatTriggeredAbility(
            new AlacrianArmoryAnimateEffect()
        );
        animateAbility.addTarget(new TargetPermanent(0, 1, mountOrVehicleFilter));
        this.addAbility(animateAbility);
    }

    private AlacrianArmory(final AlacrianArmory card) {
        super(card);
    }

    @Override
    public AlacrianArmory copy() {
        return new AlacrianArmory(this);
    }
}

class AlacrianArmoryAnimateEffect extends OneShotEffect {

    AlacrianArmoryAnimateEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to one target Mount or Vehicle you control. " +
                         "Until end of turn, that permanent becomes saddled if it's a Mount " +
                         "and becomes an artifact creature if it's a Vehicle";
    }

    private AlacrianArmoryAnimateEffect(final AlacrianArmoryAnimateEffect effect) {
        super(effect);
    }

    @Override
    public AlacrianArmoryAnimateEffect copy() {
        return new AlacrianArmoryAnimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        if (target.hasSubtype(SubType.MOUNT, game)) {
            SaddleAbility.applySaddle(target, game);
        }
        if (target.hasSubtype(SubType.VEHICLE, game)) {
            game.addEffect(new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.CREATURE, CardType.ARTIFACT), source);
        }
        return true;
    }
}
