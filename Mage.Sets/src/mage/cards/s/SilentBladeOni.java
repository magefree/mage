
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class SilentBladeOni extends CardImpl {

    public SilentBladeOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Ninjutsu {4}{U}{B}
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{4}{U}{B}")));
        // Whenever Silent-Blade Oni deals combat damage to a player, look at that player's hand. You may cast a nonland card in it without paying that card's mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SilentBladeOniEffect(), false, true));

    }

    public SilentBladeOni(final SilentBladeOni card) {
        super(card);
    }

    @Override
    public SilentBladeOni copy() {
        return new SilentBladeOni(this);
    }
}

class SilentBladeOniEffect extends OneShotEffect {

    public SilentBladeOniEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "look at that player's hand. You may cast a nonland card in it without paying that card's mana cost";
    }

    public SilentBladeOniEffect(final SilentBladeOniEffect effect) {
        super(effect);
    }

    @Override
    public SilentBladeOniEffect copy() {
        return new SilentBladeOniEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(opponent.getHand());
            if (!cardsInHand.isEmpty()) {
                TargetCard target = new TargetCard(1, Zone.HAND, new FilterNonlandCard());
                if (controller.chooseTarget(outcome, cardsInHand, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                    }
                }

            }
            return true;
        }
        return false;
    }
}
