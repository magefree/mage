package mage.cards.n;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamageDoneWatcher;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class NeedleDrop extends CardImpl {

    private static final FilterCreaturePlayerOrPlaneswalker filer = new FilterCreaturePlayerOrPlaneswalker();

    static {
        filer.getPlayerFilter().add(new DamagedThisTurnPredicate());
        filer.getPermanentFilter().add(new DamagedThisTurnPredicate());
    }

    public NeedleDrop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Needle Drop deals 1 damage to any target that was dealt damage this turn.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to any target that was dealt damage this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget(1, 1, filer));

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

class DamagedThisTurnPredicate implements Predicate<MageItem> {

    @Override
    public boolean apply(MageItem input, Game game) {
        DamageDoneWatcher watcher = game.getState().getWatcher(DamageDoneWatcher.class);
        if (watcher != null) {
            if (input instanceof MageObject) {
                return watcher.isDamaged(input.getId(), ((MageObject) input).getZoneChangeCounter(game), game);
            }
            if (input instanceof Player) {
                return watcher.isDamaged(input.getId(), 0, game);
            }
        }
        return false;
    }

}
