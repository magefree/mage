
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MoltenPrimordial extends CardImpl {

    public MoltenPrimordial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Molten Primordial enters the battlefield, for each opponent, take control of up to one target creature that player controls until end of turn. Untap those creatures. They have haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MoltenPrimordialEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(0,1));
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        this.addAbility(ability);
    }

    private MoltenPrimordial(final MoltenPrimordial card) {
        super(card);
    }

    @Override
    public MoltenPrimordial copy() {
        return new MoltenPrimordial(this);
    }
}

class MoltenPrimordialEffect extends OneShotEffect {

    MoltenPrimordialEffect() {
        super(Outcome.GainControl);
        this.staticText = "for each opponent, gain control of up to one target creature that player controls until end of turn. Untap those creatures. They gain haste until end of turn";
    }

    private MoltenPrimordialEffect(final MoltenPrimordialEffect effect) {
        super(effect);
    }

    @Override
    public MoltenPrimordialEffect copy() {
        return new MoltenPrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetCreaturePermanent) {
                Permanent targetCreature = game.getPermanent(target.getFirstTarget());
                if (targetCreature != null) {
                    ContinuousEffect effect1 = new GainControlTargetEffect(Duration.EndOfTurn);
                    effect1.setTargetPointer(new FixedTarget(targetCreature.getId(), game));
                    game.addEffect(effect1, source);

                    ContinuousEffect effect2 = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    effect2.setTargetPointer(new FixedTarget(targetCreature.getId(), game));
                    game.addEffect(effect2, source);

                    targetCreature.untap(game);
                    result = true;
                }
            }
        }
        return result;
    }
}
