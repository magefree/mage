package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpoilsOfTheHunt extends CardImpl {

    public SpoilsOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature you control gets +1/+0 until end of turn for each mana from a Treasure that was spent to cast this spell. Then that creature deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new SpoilsOfTheHuntEffect());
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect().setText(
                "Then that creature deals damage equal to its power to target creature an opponent controls"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private SpoilsOfTheHunt(final SpoilsOfTheHunt card) {
        super(card);
    }

    @Override
    public SpoilsOfTheHunt copy() {
        return new SpoilsOfTheHunt(this);
    }
}

class SpoilsOfTheHuntEffect extends OneShotEffect {

    SpoilsOfTheHuntEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control gets +1/+0 until end of turn " +
                "for each mana from a Treasure that was spent to cast this spell";
    }

    private SpoilsOfTheHuntEffect(final SpoilsOfTheHuntEffect effect) {
        super(effect);
    }

    @Override
    public SpoilsOfTheHuntEffect copy() {
        return new SpoilsOfTheHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int treasurePaid = ManaPaidSourceWatcher.getTreasurePaid(source.getId(), game);
        game.addEffect(new BoostTargetEffect(
                treasurePaid, 0, Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(source.getFirstTarget(), game)), source);
        return true;
    }
}
