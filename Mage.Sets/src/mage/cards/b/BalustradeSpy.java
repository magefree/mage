
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class BalustradeSpy extends CardImpl {

    public BalustradeSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Balustrade Spy enters the battlefield, target player reveals cards from the top of their library until they reveal a land card, then puts those cards into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BalustradeSpyEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private BalustradeSpy(final BalustradeSpy card) {
        super(card);
    }

    @Override
    public BalustradeSpy copy() {
        return new BalustradeSpy(this);
    }
}

class BalustradeSpyEffect extends OneShotEffect {

    public BalustradeSpyEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals cards from the top of their library until they reveal a land card, then puts those cards into their graveyard";
    }

    private BalustradeSpyEffect(final BalustradeSpyEffect effect) {
        super(effect);
    }

    @Override
    public BalustradeSpyEffect copy() {
        return new BalustradeSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null) {
            return false;
        }
        CardsImpl toGraveyard = new CardsImpl();
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card != null) {
                toGraveyard.add(card);
                if (card.isLand(game)) {
                    break;
                }
            }
        }
        controller.revealCards(source, toGraveyard, game);
        controller.moveCards(toGraveyard, Zone.GRAVEYARD, source, game);
        return true;
    }
}
