package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class CerebralEruption extends CardImpl {

    public CerebralEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Target opponent reveals the top card of their library. Cerebral Eruption deals damage equal to the revealed card's converted mana cost to that player and each creature they control. If a land card is revealed this way, return Cerebral Eruption to its owner's hand.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new CerebralEruptionEffect());
    }

    private CerebralEruption(final CerebralEruption card) {
        super(card);
    }

    @Override
    public CerebralEruption copy() {
        return new CerebralEruption(this);
    }
}

class CerebralEruptionEffect extends OneShotEffect {


    CerebralEruptionEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals the top card of their library. {this} deals damage equal to the revealed card's mana value to that player and each creature they control. If a land card is revealed this way, return {this} to its owner's hand";
    }

    CerebralEruptionEffect(final CerebralEruptionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null && player.getLibrary().hasCards()) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            player.revealCards(sourceObject.getIdName(), cards, game);
            game.getState().setValue(source.getSourceId().toString(), card);
            int damage = card.getManaValue();
            player.damage(damage, source.getSourceId(), source, game);
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, player.getId(), game)) {
                perm.damage(damage, source.getSourceId(), source, game, false, true);
            }
            if (card.isLand(game)) {
                Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
                if (spellCard != null) {
                    player.moveCards(spellCard, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CerebralEruptionEffect copy() {
        return new CerebralEruptionEffect(this);
    }
}
