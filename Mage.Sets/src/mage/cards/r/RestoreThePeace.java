package mage.cards.r;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.SourceDidDamageWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RestoreThePeace extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(RestoreThePeacePredicate.instance);
    }

    public RestoreThePeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{U}");

        // Return each creature that dealt damage this turn to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter)
                .setText("return each creature that dealt damage this turn to its owner's hand"));
        this.getSpellAbility().addWatcher(new SourceDidDamageWatcher());
    }

    private RestoreThePeace(final RestoreThePeace card) {
        super(card);
    }

    @Override
    public RestoreThePeace copy() {
        return new RestoreThePeace(this);
    }
}

enum RestoreThePeacePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        SourceDidDamageWatcher watcher = game.getState().getWatcher(SourceDidDamageWatcher.class);
        return watcher != null && watcher.checkSource(input, game);
    }
}
