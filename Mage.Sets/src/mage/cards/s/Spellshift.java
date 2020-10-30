
package mage.cards.s;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author Styxo
 */
public final class Spellshift extends CardImpl {

    public Spellshift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Counter target instant or sorcery spell.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterInstantOrSorcerySpell()));
        this.getSpellAbility().addEffect(new CounterTargetEffect());

        // Its controller reveals cards from the top of their library until they reveal an instant or sorcery card. That player may cast that card without paying its mana cost. Then they shuffle their library.
        this.getSpellAbility().addEffect(new SpellshiftEffect());
    }

    public Spellshift(final Spellshift card) {
        super(card);
    }

    @Override
    public Spellshift copy() {
        return new Spellshift(this);
    }
}

class SpellshiftEffect extends OneShotEffect {

    public SpellshiftEffect() {
        super(Outcome.Detriment);
        this.staticText = "Its controller reveals cards from the top of their library until they reveal an instant or sorcery card. That player may cast that card without paying its mana cost. Then they shuffle their library";
    }

    public SpellshiftEffect(final SpellshiftEffect effect) {
        super(effect);
    }

    @Override
    public SpellshiftEffect copy() {
        return new SpellshiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player spellController = game.getPlayer(((Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK)).getControllerId());
        if (spellController != null) {
            Cards cardsToReveal = new CardsImpl();
            Card toCast = null;
            for (Card card : spellController.getLibrary().getCards(game)) {
                cardsToReveal.add(card);
                if (card.isSorcery() || card.isInstant()) {
                    toCast = card;
                    break;
                }
            }
            spellController.revealCards(source, cardsToReveal, game);
            if (toCast != null && spellController.chooseUse(outcome, "Cast " + toCast.getLogName() + " without paying its mana cost?", source, game)) {
                spellController.cast(toCast.getSpellAbility(), game, true, new ApprovingObject(source, game));
            }
            spellController.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
