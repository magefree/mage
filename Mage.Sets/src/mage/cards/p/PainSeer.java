
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PainSeer extends CardImpl {

    public PainSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Inspired</i> &mdash; Whenever Pain Seer becomes untapped, reveal the top card of your library and put that card into your hand. You lose life equal to that card's converted mana cost.
        this.addAbility(new InspiredAbility(new PainSeerEffect()));
    }

    public PainSeer(final PainSeer card) {
        super(card);
    }

    @Override
    public PainSeer copy() {
        return new PainSeer(this);
    }
}

class PainSeerEffect extends OneShotEffect {

    public PainSeerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library and put that card into your hand. You lose life equal to that card's converted mana cost";
    }

    public PainSeerEffect(final PainSeerEffect effect) {
        super(effect);
    }

    @Override
    public PainSeerEffect copy() {
        return new PainSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLibrary().hasCards()) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Pain Seer", cards, game);

            if (card != null &&
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false)) {
                player.loseLife(card.getConvertedManaCost(), game, false);
                return true;
            }
        }
        return false;
    }
}
