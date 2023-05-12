package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GodEternalDiesTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class GodEternalRhonas extends CardImpl {

    public GodEternalRhonas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When God-Eternal Rhonas enters the battlefield, double the power of each other creature you control until end of turn. Those creatures gain vigilance until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GodEternalRhonasEffect()));

        // When God-Eternal Rhonas dies or is put into exile from the battlefield, you may put it into its owner's library third from the top.
        this.addAbility(new GodEternalDiesTriggeredAbility());
    }

    private GodEternalRhonas(final GodEternalRhonas card) {
        super(card);
    }

    @Override
    public GodEternalRhonas copy() {
        return new GodEternalRhonas(this);
    }
}

class GodEternalRhonasEffect extends OneShotEffect {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    GodEternalRhonasEffect() {
        super(Outcome.Benefit);
        staticText = "double the power of each other creature you control until end of turn. "
                + "Those creatures gain vigilance until end of turn.";
    }

    private GodEternalRhonasEffect(final GodEternalRhonasEffect effect) {
        super(effect);
    }

    @Override
    public GodEternalRhonasEffect copy() {
        return new GodEternalRhonasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent godEternalRhonas = game.getPermanent(source.getSourceId());
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent == null
                    || godEternalRhonas != null
                    && permanent == godEternalRhonas) {
                continue;
            }
            ContinuousEffect effect = new BoostTargetEffect(
                    permanent.getPower().getValue(),
                    0, Duration.EndOfTurn
            );
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);

            ContinuousEffect effect2 = new GainAbilityTargetEffect(
                    VigilanceAbility.getInstance(),
                    Duration.EndOfTurn
            );
            effect2.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect2, source);
        }
        return true;
    }
}
