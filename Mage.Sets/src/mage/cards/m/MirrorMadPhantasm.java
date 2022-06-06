
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class MirrorMadPhantasm extends CardImpl {

    public MirrorMadPhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // {1}{U}: Mirror-Mad Phantasm's owner shuffles it into their library. If that player does, they reveal cards from the top of that library until a card named Mirror-Mad Phantasm is revealed. That player puts that card onto the battlefield and all other cards revealed this way into their graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MirrorMadPhantasmEffect(), new ManaCostsImpl<>("{1}{U}")));

    }

    private MirrorMadPhantasm(final MirrorMadPhantasm card) {
        super(card);
    }

    @Override
    public MirrorMadPhantasm copy() {
        return new MirrorMadPhantasm(this);
    }
}

class MirrorMadPhantasmEffect extends OneShotEffect {

    public MirrorMadPhantasmEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this}'s owner shuffles it into their library. If that player does, they reveal cards from the top of that library until a card named Mirror-Mad Phantasm is revealed. That player puts that card onto the battlefield and all other cards revealed this way into their graveyard";
    }

    public MirrorMadPhantasmEffect(final MirrorMadPhantasmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = source.getSourcePermanentIfItStillExists(game);
        if (perm != null) {
            Player owner = game.getPlayer(perm.getOwnerId());
            if (owner == null) {
                return false;
            }
            if (owner.shuffleCardsToLibrary(perm, game, source)) {
                Cards cards = new CardsImpl();
                Card phantasmCard = null;
                for (Card card : owner.getLibrary().getCards(game)) {
                    cards.add(card);
                    if (card.getName().equals("Mirror-Mad Phantasm")) {
                        phantasmCard = card;
                        break;
                    }
                }
                owner.revealCards(source, cards, game);
                if (phantasmCard != null) {
                    owner.moveCards(phantasmCard, Zone.BATTLEFIELD, source, game);
                    cards.remove(phantasmCard);
                }
                owner.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public MirrorMadPhantasmEffect copy() {
        return new MirrorMadPhantasmEffect(this);
    }

}
