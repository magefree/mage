
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class CranialArchive extends CardImpl {

    public CranialArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {2}, Exile Cranial Archive: Target player shuffles their graveyard into their library. Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CranialArchiveEffect(), new GenericManaCost(2));
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private CranialArchive(final CranialArchive card) {
        super(card);
    }

    @Override
    public CranialArchive copy() {
        return new CranialArchive(this);
    }
}

class CranialArchiveEffect extends OneShotEffect {

    public CranialArchiveEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player shuffles their graveyard into their library. Draw a card";
    }

    private CranialArchiveEffect(final CranialArchiveEffect effect) {
        super(effect);
    }

    @Override
    public CranialArchiveEffect copy() {
        return new CranialArchiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                for (Card card: targetPlayer.getGraveyard().getCards(game)){
                    targetPlayer.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
                }
                targetPlayer.shuffleLibrary(source, game);
            }
            controller.drawCards(1, source, game);
            return true;
        }
        return false;
    }
}
