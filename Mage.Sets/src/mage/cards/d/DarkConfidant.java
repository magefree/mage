
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class DarkConfidant extends CardImpl {

    public DarkConfidant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DarkConfidantEffect(), TargetController.YOU, false));
    }

    private DarkConfidant(final DarkConfidant card) {
        super(card);
    }

    @Override
    public DarkConfidant copy() {
        return new DarkConfidant(this);
    }
}

class DarkConfidantEffect extends OneShotEffect {

    DarkConfidantEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library and put that card into your hand. You lose life equal to its mana value";
    }

    private DarkConfidantEffect(final DarkConfidantEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    Cards cards = new CardsImpl(card);
                    controller.revealCards(sourcePermanent.getIdName(), cards, game);
                    controller.moveCards(card, Zone.HAND, source, game);
                    controller.loseLife(card.getManaValue(), game, source, false);

                }
                return true;
            }
        }
        return false;
    }

    @Override
    public DarkConfidantEffect copy() {
        return new DarkConfidantEffect(this);
    }
}
