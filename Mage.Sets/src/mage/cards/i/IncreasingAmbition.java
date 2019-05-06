
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward
 */
public final class IncreasingAmbition extends CardImpl {

    public IncreasingAmbition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");


        // Search your library for a card and put that card into your hand. If this spell was cast from a graveyard, instead search your library for two cards and put those cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new IncreasingAmbitionEffect());

        // Flashback {7}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{7}{B}"), TimingRule.SORCERY));
    }

    public IncreasingAmbition(final IncreasingAmbition card) {
        super(card);
    }

    @Override
    public IncreasingAmbition copy() {
        return new IncreasingAmbition(this);
    }
}

class IncreasingAmbitionEffect extends SearchEffect {

    public IncreasingAmbitionEffect() {
        super(new TargetCardInLibrary(), Outcome.DrawCard);
        staticText = "Search your library for a card and put that card into your hand. If this spell was cast from a graveyard, instead search your library for two cards and put those cards into your hand. Then shuffle your library";
    }

    public IncreasingAmbitionEffect(final IncreasingAmbitionEffect effect) {
        super(effect);
    }

    @Override
    public IncreasingAmbitionEffect copy() {
        return new IncreasingAmbitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
            if (spell != null) {
                if (spell.getFromZone() == Zone.GRAVEYARD) {
                    target = new TargetCardInLibrary(2, new FilterCard());
                }
                else {
                    target = new TargetCardInLibrary();
                }
                if (player.searchLibrary(target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        for (UUID cardId: target.getTargets()) {
                            Card card = player.getLibrary().remove(cardId, game);
                            if (card != null) {
                                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                            }
                        }
                    }
                }
                // shuffle anyway
                player.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }

}
