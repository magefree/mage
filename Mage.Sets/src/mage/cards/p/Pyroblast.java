package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class Pyroblast extends CardImpl {

    public Pyroblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Choose one - Counter target spell if it's blue; or destroy target permanent if it's blue.
        this.getSpellAbility().addEffect(new PyroblastCounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        Mode mode = new Mode(new PyroblastDestroyTargetEffect());
        mode.addTarget(new TargetPermanent());

        this.getSpellAbility().addMode(mode);
    }

    private Pyroblast(final Pyroblast card) {
        super(card);
    }

    @Override
    public Pyroblast copy() {
        return new Pyroblast(this);
    }
}

class PyroblastCounterTargetEffect extends OneShotEffect {

    public PyroblastCounterTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell if it's blue";
    }

    public PyroblastCounterTargetEffect(final PyroblastCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public PyroblastCounterTargetEffect copy() {
        return new PyroblastCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
        if (targetSpell != null && targetSpell.getColor(game).isBlue()) {
            game.getStack().counter(source.getFirstTarget(), source, game);
        }
        return true;
    }
}

class PyroblastDestroyTargetEffect extends OneShotEffect {

    public PyroblastDestroyTargetEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target permanent if it's blue";
    }

    public PyroblastDestroyTargetEffect(final PyroblastDestroyTargetEffect effect) {
        super(effect);
    }

    @Override
    public PyroblastDestroyTargetEffect copy() {
        return new PyroblastDestroyTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (permanent != null && permanent.getColor(game).isBlue()) {
            permanent.destroy(source, game, false);
        }
        return true;
    }
}
