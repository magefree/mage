
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class ElementalAugury extends CardImpl {

    public ElementalAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");

        // {3}: Look at the top three cards of target player's library, then put them back in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElementalAuguryEffect(), new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ElementalAugury(final ElementalAugury card) {
        super(card);
    }

    @Override
    public ElementalAugury copy() {
        return new ElementalAugury(this);
    }
}

class ElementalAuguryEffect extends OneShotEffect {

    public ElementalAuguryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of target player's library, then put them back in any order";
    }

    public ElementalAuguryEffect(final ElementalAuguryEffect effect) {
        super(effect);
    }

    @Override
    public ElementalAuguryEffect copy() {
        return new ElementalAuguryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null
                || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetPlayer.getLibrary().getTopCards(game, 3));
        controller.lookAtCards(source, null, cards, game);
        controller.putCardsOnTopOfLibrary(cards, game, source, true);
        return true;
    }
}
