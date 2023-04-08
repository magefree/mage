package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SearingBarb extends CardImpl {

    public SearingBarb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Searing Barb deals 2 damage to any target. If it's a creature, it can't block this turn. Incubate 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new SearingBarbEffect());
        this.getSpellAbility().addEffect(new IncubateEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SearingBarb(final SearingBarb card) {
        super(card);
    }

    @Override
    public SearingBarb copy() {
        return new SearingBarb(this);
    }
}

class SearingBarbEffect extends OneShotEffect {

    SearingBarbEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a creature, it can't block this turn";
    }

    private SearingBarbEffect(final SearingBarbEffect effect) {
        super(effect);
    }

    @Override
    public SearingBarbEffect copy() {
        return new SearingBarbEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        game.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
