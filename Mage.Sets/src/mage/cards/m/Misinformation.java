
package mage.cards.m;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author L_J
 */
public final class Misinformation extends CardImpl {

    public Misinformation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Put up to three target cards from an opponent's graveyard on top of their library in any order.
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(0, 3, new FilterCard("cards from an opponent's graveyard"), true));
        this.getSpellAbility().addEffect(new MisinformationEffect());
    }

    private Misinformation(final Misinformation card) {
        super(card);
    }

    @Override
    public Misinformation copy() {
        return new Misinformation(this);
    }
}

class MisinformationEffect extends OneShotEffect {
    
    MisinformationEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put up to three target cards from an opponent's graveyard on top of their library in any order";
    }
    
    private MisinformationEffect(final MisinformationEffect effect) {
        super(effect);
    }
    
    @Override
    public MisinformationEffect copy() {
        return new MisinformationEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> targets = this.getTargetPointer().getTargets(game, source);
            if (targets != null) {
                Cards cards = new CardsImpl(targets);
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
                return true;
            }
        }
        return false;
    }
}
