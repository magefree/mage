
package mage.abilities.effects.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;

/**
 *
 * @author LevelX2
 */
public class ManifestEffect extends OneShotEffect {

    private final int amount;

    public ManifestEffect(int amount) {
        super(Outcome.PutCreatureInPlay);
        this.amount = amount;
        this.staticText = setText();
    }

    public ManifestEffect(final ManifestEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public ManifestEffect copy() {
        return new ManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Ability newSource = source.copy();
            newSource.setWorksFaceDown(true);
            Set<Card> cards = controller.getLibrary().getTopCards(game, amount);
            for (Card card : cards) {
                ManaCosts manaCosts = null;
                if (card.isCreature(game)) {
                    manaCosts = card.getSpellAbility() != null ? card.getSpellAbility().getManaCosts() : null;
                    if (manaCosts == null) {
                        manaCosts = new ManaCostsImpl<>("{0}");
                    }
                }
                MageObjectReference objectReference = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) + 1, game);
                game.addEffect(new BecomesFaceDownCreatureEffect(manaCosts, objectReference, Duration.Custom, FaceDownType.MANIFESTED), newSource);

            }
            controller.moveCards(cards, Zone.BATTLEFIELD, source, game, false, true, false, null);
            for (Card card : cards) {
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    permanent.setManifested(true);
                }
            }
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("manifest the top ");
        if (amount > 1) {
            sb.append(CardUtil.numberToText(amount)).append(" cards ");
        } else {
            sb.append("card ");
        }
        sb.append("of your library. ");
        if (amount > 1) {
            sb.append("<i>(To manifest a card, put it onto the battlefield face down as a 2/2 creature. You may turn it face up at any time for its mana cost if it's a creature card.)</i>");
        } else {
            sb.append("<i>(Put it onto the battlefield face down as a 2/2 creature. Turn it face up at any time for its mana cost if it's a creature card.)</i>");
        }
        return sb.toString();
    }
}
