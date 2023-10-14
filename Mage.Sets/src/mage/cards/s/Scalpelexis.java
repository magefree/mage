
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author jeffwadsworth
 */
public final class Scalpelexis extends CardImpl {

    public Scalpelexis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Scalpelexis deals combat damage to a player, that player exiles the top four cards of their library. If two or more of those cards have the same name, repeat this process.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ScalpelexisEffect(), false, true));
    }

    private Scalpelexis(final Scalpelexis card) {
        super(card);
    }

    @Override
    public Scalpelexis copy() {
        return new Scalpelexis(this);
    }
}

class ScalpelexisEffect extends OneShotEffect {

    public ScalpelexisEffect() {
        super(Outcome.Exile);
        this.staticText = "that player exiles the top four cards of their library. If two or more of those cards have the same name, repeat this process";
    }

    private ScalpelexisEffect(final ScalpelexisEffect effect) {
        super(effect);
    }

    @Override
    public ScalpelexisEffect copy() {
        return new ScalpelexisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        Set<String> cardNames = new HashSet<>();
        boolean doubleName;
        do {
            doubleName = false;
            Cards toExile = new CardsImpl(targetPlayer.getLibrary().getTopCards(game, 4));
            cardNames.clear();
            for (Card card : toExile.getCards(game)) {
                if (cardNames.contains(card.getName())) {
                    doubleName = true;
                    break;
                } else {
                    cardNames.add(card.getName());
                }
            }
            targetPlayer.moveCards(toExile, Zone.EXILED, source, game);
        } while (doubleName);
        return true;
    }
}
