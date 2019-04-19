package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GodEternalDiesTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GodEternalRhonas extends CardImpl {

    public GodEternalRhonas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
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

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    GodEternalRhonasEffect() {
        super(Outcome.Benefit);
        staticText = "double the power of each other creature you control until end of turn. " +
                "Those creatures gain vigilance until end of turn.";
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
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent == null) {
                continue;
            }
            ContinuousEffect effect = new BoostTargetEffect(
                    permanent.getPower().getValue(),
                    0, Duration.EndOfTurn
            );
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        game.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(),
                Duration.EndOfTurn, filter
        ), source);
        return true;
    }
}