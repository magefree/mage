package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ArrestersZeal extends CardImpl {

    public ArrestersZeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");


        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Addendum — If you cast this spell during your main phase, that creature gains flying until end of turn.
        this.getSpellAbility().addEffect(new ArrestersZealEffect());
    }

    private ArrestersZeal(final ArrestersZeal card) {
        super(card);
    }

    @Override
    public ArrestersZeal copy() {
        return new ArrestersZeal(this);
    }
}

class ArrestersZealEffect extends OneShotEffect {

    ArrestersZealEffect() {
        super(Outcome.Benefit);
        staticText = "<br><i>Addendum</i> &mdash; If you cast this spell during your main phase, that creature gains flying until end of turn.";
    }

    private ArrestersZealEffect(final ArrestersZealEffect effect) {
        super(effect);
    }

    @Override
    public ArrestersZealEffect copy() {
        return new ArrestersZealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!AddendumCondition.instance.apply(game, source)) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), source);
        return true;
    }
}
