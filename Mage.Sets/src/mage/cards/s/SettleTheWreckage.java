
package mage.cards.s;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class SettleTheWreckage extends CardImpl {

    public SettleTheWreckage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Exile all attacking creatures target player controls. That player may search their library for that many basic land cards, put those cards onto the battlefield tapped, then shuffle their library.
        this.getSpellAbility().addEffect(new SettleTheWreckageEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SettleTheWreckage(final SettleTheWreckage card) {
        super(card);
    }

    @Override
    public SettleTheWreckage copy() {
        return new SettleTheWreckage(this);
    }
}

class SettleTheWreckageEffect extends OneShotEffect {

    SettleTheWreckageEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile all attacking creatures target player controls. That player may search their library for that many basic land cards, put those cards onto the battlefield tapped, then shuffle";
    }

    SettleTheWreckageEffect(final SettleTheWreckageEffect effect) {
        super(effect);
    }

    @Override
    public SettleTheWreckageEffect copy() {
        return new SettleTheWreckageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || player == null) {
            return false;
        }
        int attackers = 0;
        Set<Card> toExile = new HashSet<>();
        Iterator<UUID> creatureIds = game.getCombat().getAttackers().iterator();
        while (creatureIds.hasNext()) {
            Permanent creature = game.getPermanent(creatureIds.next());
            if (creature != null && creature.isControlledBy(player.getId())) {
                toExile.add(creature);
                attackers++;
            }
        }
        controller.moveCards(toExile, Zone.EXILED, source, game);
        TargetCardInLibrary target = new TargetCardInLibrary(0, attackers, StaticFilters.FILTER_CARD_BASIC_LAND);
        if (player.chooseUse(Outcome.Benefit, "Search for up to " + attackers + " basic land" + ((attackers == 1) ? "" : "s") + "?", source, game) && player.searchLibrary(target, source, game)) {
            player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
            player.shuffleLibrary(source, game);
        }
        return true;

    }
}
