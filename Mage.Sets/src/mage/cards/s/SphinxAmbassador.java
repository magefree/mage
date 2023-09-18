
package mage.cards.s;

import java.util.Collection;
import java.util.TreeSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class SphinxAmbassador extends CardImpl {

    public SphinxAmbassador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Sphinx Ambassador deals combat damage to a player, search that player's library for a card, then that player names a card. If you searched for a creature card that isn't the named card, you may put it onto the battlefield under your control. Then that player shuffles their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SphinxAmbassadorEffect(), false, true));
    }

    private SphinxAmbassador(final SphinxAmbassador card) {
        super(card);
    }

    @Override
    public SphinxAmbassador copy() {
        return new SphinxAmbassador(this);
    }
}

class SphinxAmbassadorEffect extends OneShotEffect {

    public SphinxAmbassadorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "search that player's library for a card, then that player chooses a card name. If you searched for a creature card that doesn't have that name, you may put it onto the battlefield under your control. Then that player shuffles";
    }

    private SphinxAmbassadorEffect(final SphinxAmbassadorEffect effect) {
        super(effect);
    }

    @Override
    public SphinxAmbassadorEffect copy() {
        return new SphinxAmbassadorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && targetPlayer != null && sourcePermanent != null) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            controller.searchLibrary(target, source, game, targetPlayer.getId());

            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                TreeSet<String> choices = new TreeSet<>();
                Collection<Card> cards = game.getCards();
                for (Card gameCard : cards) {
                    if (gameCard.isOwnedBy(targetPlayer.getId())) {
                        choices.add(gameCard.getName());
                    }
                }

                Choice cardChoice = new ChoiceImpl(false, ChoiceHintType.CARD);
                cardChoice.setChoices(choices);
                cardChoice.clearChoice();
                if (!targetPlayer.choose(Outcome.Benefit, cardChoice, game)) {
                    return false;
                }
                String cardName = cardChoice.getChoice();

                game.informPlayers(sourcePermanent.getName() + ", named card: [" + cardName + ']');
                if (!card.getName().equals(cardName) && card.isCreature(game)) {
                    if (controller.chooseUse(outcome, "Put " + card.getName() + " onto the battlefield?", source, game)) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }

            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
