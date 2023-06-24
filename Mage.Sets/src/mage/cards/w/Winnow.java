
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LoneFox
 */
public final class Winnow extends CardImpl {

    public Winnow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Destroy target nonland permanent if another permanent with the same name is on the battlefield.
        this.getSpellAbility().addEffect(new WinnowEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Winnow(final Winnow card) {
        super(card);
    }

    @Override
    public Winnow copy() {
        return new Winnow(this);
    }
}

class WinnowEffect extends DestroyTargetEffect {

    public WinnowEffect() {
        super();
        staticText = "Destroy target nonland permanent if another permanent with the same name is on the battlefield";
    }

    public WinnowEffect(final WinnowEffect effect) {
        super(effect);
    }

    @Override
    public WinnowEffect copy() {
        return new WinnowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if(target != null) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(new NamePredicate(target.getName()));
            if(new PermanentsOnBattlefieldCount(filter).calculate(game, source, this) > 1) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
