package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ElixirOfImmortality extends CardImpl {

    public ElixirOfImmortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElixerOfImmortalityEffect(), new TapSourceCost());
        ability.addCost(new GenericManaCost(2));
        this.addAbility(ability);
    }

    private ElixirOfImmortality(final ElixirOfImmortality card) {
        super(card);
    }

    @Override
    public ElixirOfImmortality copy() {
        return new ElixirOfImmortality(this);
    }

}

class ElixerOfImmortalityEffect extends OneShotEffect {

    public ElixerOfImmortalityEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 5 life. Shuffle {this} and your graveyard into their owner's library";
    }

    private ElixerOfImmortalityEffect(final ElixerOfImmortalityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null) {
            player.gainLife(5, game, source);
            if (permanent != null) {
                player.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, true);
            }
            for (Card card: player.getGraveyard().getCards(game)) {
                player.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            }                 
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public ElixerOfImmortalityEffect copy() {
        return new ElixerOfImmortalityEffect(this);
    }

}
