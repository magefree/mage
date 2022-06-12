
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class PhyrexianFurnace extends CardImpl {

    public PhyrexianFurnace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {tap}: Exile the bottom card of target player's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianFurnaceEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {1}, Sacrifice Phyrexian Furnace: Exile target card from a graveyard. Draw a card.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private PhyrexianFurnace(final PhyrexianFurnace card) {
        super(card);
    }

    @Override
    public PhyrexianFurnace copy() {
        return new PhyrexianFurnace(this);
    }
}

class PhyrexianFurnaceEffect extends OneShotEffect {

    public PhyrexianFurnaceEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the bottom card of target player's graveyard";
    }

    public PhyrexianFurnaceEffect(final PhyrexianFurnaceEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianFurnaceEffect copy() {
        return new PhyrexianFurnaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Card bottomCard = null;
            for (Card card : player.getGraveyard().getCards(game)) {
                bottomCard = card;
                break;
            }
            if (bottomCard != null) {
                return player.moveCardToExileWithInfo(bottomCard, null, "", source, game, Zone.GRAVEYARD, true);
            }
            return true;
        }
        return false;
    }
}
