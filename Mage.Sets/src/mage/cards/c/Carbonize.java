package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.DiesReplacementEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 *
 * @author markedagain
 */
public final class Carbonize extends CardImpl {

    public Carbonize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Carbonize deals 3 damage to any target. If it's a creature, it can't be regenerated this turn, and if it would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new CarbonizeEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());

    }

    private Carbonize(final Carbonize card) {
        super(card);
    }

    @Override
    public Carbonize copy() {
        return new Carbonize(this);
    }
}

class CarbonizeEffect extends OneShotEffect {

    CarbonizeEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a creature, it can't be regenerated this turn, and if it would die this turn, exile it instead";
    }

    private CarbonizeEffect(final CarbonizeEffect effect) {
        super(effect);
    }

    @Override
    public CarbonizeEffect copy() {
        return new CarbonizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        game.addEffect(new CantRegenerateTargetEffect(Duration.EndOfTurn, "")
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new DiesReplacementEffect(new MageObjectReference(permanent, game), Duration.EndOfTurn), source);
        return true;
    }
}
