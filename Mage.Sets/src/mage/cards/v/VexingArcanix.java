
package mage.cards.v;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com & L_J
 */
public final class VexingArcanix extends CardImpl {

    public VexingArcanix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: Target player chooses a card name, then reveals the top card of their library. If that card has the chosen name, the player puts it into their hand. Otherwise, the player puts it into their graveyard and Vexing Arcanix deals 2 damage to him or her.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VexingArcanixEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public VexingArcanix(final VexingArcanix card) {
        super(card);
    }

    @Override
    public VexingArcanix copy() {
        return new VexingArcanix(this);
    }

}

class VexingArcanixEffect extends OneShotEffect {

    public VexingArcanixEffect() {
        super(Outcome.DrawCard);
        staticText = "Target player chooses a card name, then reveals the top card of their library. If that card has the chosen name, the player puts it into their hand. Otherwise, the player puts it into their graveyard and {this} deals 2 damage to him or her";
    }

    public VexingArcanixEffect(final VexingArcanixEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (sourceObject != null && player != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNames());
            cardChoice.setMessage("Name a card");
            if (!player.choose(Outcome.DrawCard, cardChoice, game)) {
                return false;
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers(sourceObject.getLogName() + ", player: " + player.getLogName() + ", named: [" + cardName + ']');
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                player.revealCards(sourceObject.getIdName(), cards, game);
                if (card.getName().equals(cardName)) {
                    player.moveCards(cards, Zone.HAND, source, game);
                } else {
                    player.moveCards(cards, Zone.GRAVEYARD, source, game);
                    player.damage(2, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public VexingArcanixEffect copy() {
        return new VexingArcanixEffect(this);
    }

}
