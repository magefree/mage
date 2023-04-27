
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PainfulQuandary extends CardImpl {

    public PainfulQuandary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // Whenever an opponent casts a spell, that player loses 5 life unless they discard a card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new PainfulQuandryEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER));
    }

    private PainfulQuandary(final PainfulQuandary card) {
        super(card);
    }

    @Override
    public PainfulQuandary copy() {
        return new PainfulQuandary(this);
    }

}

class PainfulQuandryEffect extends OneShotEffect {

    public PainfulQuandryEffect() {
        super(Outcome.LoseLife);
        staticText = "that player loses 5 life unless they discard a card";
    }

    public PainfulQuandryEffect(final PainfulQuandryEffect effect) {
        super(effect);
    }

    @Override
    public PainfulQuandryEffect copy() {
        return new PainfulQuandryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            boolean paid = false;
            Cost cost = new DiscardTargetCost(new TargetCardInHand());
            if (cost.canPay(source, source, player.getId(), game)
                    && player.chooseUse(Outcome.Detriment, "Discard a card (otherwise you lose 5 life)?", source, game)) {
                paid = cost.pay(source, game, source, player.getId(), false, null);
            }
            if (!paid) {
                player.loseLife(5, game, source, false);
            }
            return true;
        }
        return false;
    }

}
