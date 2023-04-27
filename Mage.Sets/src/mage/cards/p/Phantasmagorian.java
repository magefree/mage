
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public final class Phantasmagorian extends CardImpl {

    public Phantasmagorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When you cast Phantasmagorian, any player may discard three cards. If a player does, counter Phantasmagorian.
        this.addAbility(new CastSourceTriggeredAbility(new CounterSourceEffect()));
        // Discard three cards: Return Phantasmagorian from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new DiscardTargetCost(new TargetCardInHand(3, StaticFilters.FILTER_CARD_CARDS))));
    }

    private Phantasmagorian(final Phantasmagorian card) {
        super(card);
    }

    @Override
    public Phantasmagorian copy() {
        return new Phantasmagorian(this);
    }
}

class CounterSourceEffect extends OneShotEffect {

    public CounterSourceEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "any player may discard three cards. If a player does, counter {this}";
    }

    public CounterSourceEffect(final CounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public CounterSourceEffect copy() {
        return new CounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            DiscardTargetCost cost = new DiscardTargetCost(new TargetCardInHand(3, 3, new FilterCard()));
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                cost.clearPaid();
                if (player != null
                 && cost.canPay(source, source, player.getId(), game)
                        && player.chooseUse(outcome, "Discard three cards to counter " + sourceObject.getIdName() + '?', source, game)) {
                    if (cost.pay(source, game, source, playerId, false, null)) {
                        game.informPlayers(player.getLogName() + " discards 3 cards to counter " + sourceObject.getIdName() + '.');
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            game.getStack().counter(spell.getId(), source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
