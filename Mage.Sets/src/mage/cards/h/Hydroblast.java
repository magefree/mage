
package mage.cards.h;

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
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class Hydroblast extends CardImpl {

    public Hydroblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Choose one - Counter target spell if it's red;
        this.getSpellAbility().addEffect(new HydroblastCounterEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        
        // or destroy target permanent if it's red.
        Mode mode = new Mode();
        mode.getEffects().add(new HydroblastDestroyEffect());
        mode.getTargets().add(new TargetPermanent());
        this.getSpellAbility().addMode(mode);
    }

    public Hydroblast(final Hydroblast card) {
        super(card);
    }

    @Override
    public Hydroblast copy() {
        return new Hydroblast(this);
    }
}

class HydroblastCounterEffect extends OneShotEffect {

    HydroblastCounterEffect() {
        super(Outcome.Detriment);
    }

    HydroblastCounterEffect(final HydroblastCounterEffect effect) {
        super(effect);
    }

    @Override
    public HydroblastCounterEffect copy() {
        return new HydroblastCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getStack().getSpell(source.getFirstTarget()).getColor(game).isRed()) {
            game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell if it's red";
    }
}

class HydroblastDestroyEffect extends OneShotEffect {

    HydroblastDestroyEffect() {
        super(Outcome.DestroyPermanent);
    }

    HydroblastDestroyEffect(final HydroblastDestroyEffect effect) {
        super(effect);
    }

    @Override
    public HydroblastDestroyEffect copy() {
        return new HydroblastDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (permanent != null && permanent.getColor(game).isRed()) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "destroy target permanent if it's red";
    }
}
