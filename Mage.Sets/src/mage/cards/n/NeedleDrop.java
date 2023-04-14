package mage.cards.n;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;
import mage.watchers.common.DamageDoneWatcher;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class NeedleDrop extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterAnyTarget("any target that was dealt damage this turn");

    static {
        filter.getPlayerFilter().add(DamagedThisTurnPredicate.instance);
        filter.getPermanentFilter().add(DamagedThisTurnPredicate.instance);
    }

    public NeedleDrop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Needle Drop deals 1 damage to any target that was dealt damage this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private NeedleDrop(final NeedleDrop card) {
        super(card);
    }

    @Override
    public NeedleDrop copy() {
        return new NeedleDrop(this);
    }
}

enum DamagedThisTurnPredicate implements Predicate<MageItem> {
    instance;

    @Override
    public boolean apply(MageItem input, Game game) {
        DamageDoneWatcher watcher = game.getState().getWatcher(DamageDoneWatcher.class);
        if (watcher == null) {
            return false;
        }
        if (input instanceof MageObject) {
            return watcher.isDamaged(input.getId(), ((MageObject) input).getZoneChangeCounter(game), game);
        }
        if (input instanceof Player) {
            return watcher.isDamaged(input.getId(), 0, game);
        }
        return false;
    }
}
