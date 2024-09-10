package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BagOfHolding extends CardImpl {

    public BagOfHolding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Whenever you discard a card, exile that card from your graveyard.
        this.addAbility(new DiscardCardControllerTriggeredAbility(new BagOfHoldingExileEffect(), false));

        // {2}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}, {T}, Sacrifice Bag of Holding: Return all cards exiled with Bag of Holding to their owner's hand.
        ability = new SimpleActivatedAbility(new BagOfHoldingReturnCardsEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BagOfHolding(final BagOfHolding card) {
        super(card);
    }

    @Override
    public BagOfHolding copy() {
        return new BagOfHolding(this);
    }
}

class BagOfHoldingExileEffect extends OneShotEffect {

    BagOfHoldingExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile that card from your graveyard";
    }

    private BagOfHoldingExileEffect(final BagOfHoldingExileEffect effect) {
        super(effect);
    }

    @Override
    public BagOfHoldingExileEffect copy() {
        return new BagOfHoldingExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = (Card) getValue("discardedCard");
        if (player == null || card == null || !card.isOwnedBy(player.getId())
                || !Zone.GRAVEYARD.match(game.getState().getZone(card.getId()))) {
            return false;
        }
        return player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }
}

class BagOfHoldingReturnCardsEffect extends OneShotEffect {

    BagOfHoldingReturnCardsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "return all cards exiled with {this} to their owner's hand";
    }

    private BagOfHoldingReturnCardsEffect(final BagOfHoldingReturnCardsEffect effect) {
        super(effect);
    }

    @Override
    public BagOfHoldingReturnCardsEffect copy() {
        return new BagOfHoldingReturnCardsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(
                CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter())
        );
        if (exileZone == null) {
            return true;
        }
        return controller.moveCards(exileZone, Zone.HAND, source, game);
    }
}
