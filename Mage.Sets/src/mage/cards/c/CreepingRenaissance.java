
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author nantuko
 */
public final class CreepingRenaissance extends CardImpl {

    public CreepingRenaissance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Choose a permanent type. Return all cards of the chosen type from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CreepingRenaissanceEffect());

        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{5}{G}{G}"), TimingRule.SORCERY));
    }

    public CreepingRenaissance(final CreepingRenaissance card) {
        super(card);
    }

    @Override
    public CreepingRenaissance copy() {
        return new CreepingRenaissance(this);
    }
}

class CreepingRenaissanceEffect extends OneShotEffect {

    public CreepingRenaissanceEffect() {
        super(Outcome.Detriment);
        staticText = "Choose a permanent type. Return all cards of the chosen type from your graveyard to your hand";
    }

    public CreepingRenaissanceEffect(final CreepingRenaissanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice typeChoice = new ChoiceImpl();
            typeChoice.setMessage("Choose permanent type");

            typeChoice.getChoices().add(CardType.ARTIFACT.toString());
            typeChoice.getChoices().add(CardType.CREATURE.toString());
            typeChoice.getChoices().add(CardType.ENCHANTMENT.toString());
            typeChoice.getChoices().add(CardType.LAND.toString());
            typeChoice.getChoices().add(CardType.PLANESWALKER.toString());

            if (controller.choose(Outcome.ReturnToHand, typeChoice, game)) {
                String typeName = typeChoice.getChoice();
                CardType chosenType = null;
                for (CardType cardType : CardType.values()) {
                    if (cardType.toString().equals(typeName)) {
                        chosenType = cardType;
                    }
                }
                if (chosenType != null) {
                    for (Card card : controller.getGraveyard().getCards(game)) {
                        if (card.getCardType().contains(chosenType)) {
                            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CreepingRenaissanceEffect copy() {
        return new CreepingRenaissanceEffect(this);
    }

}
