package mage.cards.a;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.SourceDidDamageWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AvengingArrow extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature that dealt damage this turn");

    public AvengingArrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target creature that dealt damage this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new SourceDidDamageWatcher());
    }

    private AvengingArrow(final AvengingArrow card) {
        super(card);
    }

    @Override
    public AvengingArrow copy() {
        return new AvengingArrow(this);
    }
}

enum AvengingArrowPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        SourceDidDamageWatcher watcher = game.getState().getWatcher(SourceDidDamageWatcher.class);
        return watcher != null && watcher.checkSource(input, game);
    }
}
