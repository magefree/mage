package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorpseAppraiser extends CardImpl {

    public CorpseAppraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Corpse Appraiser enters the battlefield, exile up to one target creature card from a graveyard. If a card is put into exile this way, look at the top three cards of your library, then put one of those cards into your hand and the rest into your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CorpseAppraiserEffect());
        ability.addTarget(new TargetCardInGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        ));
        this.addAbility(ability);
    }

    private CorpseAppraiser(final CorpseAppraiser card) {
        super(card);
    }

    @Override
    public CorpseAppraiser copy() {
        return new CorpseAppraiser(this);
    }
}

class CorpseAppraiserEffect extends OneShotEffect {

    CorpseAppraiserEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target creature card from a graveyard. " +
                "If a card is put into exile this way, look at the top three cards of your library, " +
                "then put one of those cards into your hand and the rest into your graveyard";
    }

    private CorpseAppraiserEffect(final CorpseAppraiserEffect effect) {
        super(effect);
    }

    @Override
    public CorpseAppraiserEffect copy() {
        return new CorpseAppraiserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (!Zone.EXILED.match(game.getState().getZone(card.getId()))) {
            return true;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCardInLibrary();
        player.choose(outcome, cards, target, source, game);
        player.moveCards(cards.get(target.getFirstTarget(), game), Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
