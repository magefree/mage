
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author North
 */
public final class HauntedFengraf extends CardImpl {

    public HauntedFengraf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {3}, {tap}, Sacrifice Haunted Fengraf: Return a creature card at random from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HauntedFengrafEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public HauntedFengraf(final HauntedFengraf card) {
        super(card);
    }

    @Override
    public HauntedFengraf copy() {
        return new HauntedFengraf(this);
    }
}

class HauntedFengrafEffect extends OneShotEffect {

    public HauntedFengrafEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return a creature card at random from your graveyard to your hand";
    }

    public HauntedFengrafEffect(final HauntedFengrafEffect effect) {
        super(effect);
    }

    @Override
    public HauntedFengrafEffect copy() {
        return new HauntedFengrafEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card[] cards = player.getGraveyard().getCards(new FilterCreatureCard(), game).toArray(new Card[0]);
            if (cards.length > 0) {
                Card card = cards[RandomUtil.nextInt(cards.length)];
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                game.informPlayers(card.getName() + " returned to the hand of " + player.getLogName());
                return true;
            }
        }
        return false;
    }
}
