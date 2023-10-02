
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class NobleBenefactor extends CardImpl {

    public NobleBenefactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Noble Benefactor dies, each player may search their library for a card and put that card into their hand. Then each player who searched their library this way shuffles it.
        this.addAbility(new DiesSourceTriggeredAbility(new NobleBenefactorEffect()));
    }

    private NobleBenefactor(final NobleBenefactor card) {
        super(card);
    }

    @Override
    public NobleBenefactor copy() {
        return new NobleBenefactor(this);
    }
}

class NobleBenefactorEffect extends OneShotEffect {

    public NobleBenefactorEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may search their library for a card and put that card into their hand. Then each player who searched their library this way shuffles";
    }

    private NobleBenefactorEffect(final NobleBenefactorEffect effect) {
        super(effect);
    }

    @Override
    public NobleBenefactorEffect copy() {
        return new NobleBenefactorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary();
                    if (player.chooseUse(Outcome.Benefit, "Search your library for a card to put into your hand?", source, game)) {
                        player.searchLibrary(target, source, game);
                        for (UUID cardId : target.getTargets()) {
                            Card card = player.getLibrary().getCard(cardId, game);
                            if (card != null) {
                                player.moveCards(card, Zone.HAND, source, game);
                            }
                        }
                        player.shuffleLibrary(source, game);
                    }
                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}