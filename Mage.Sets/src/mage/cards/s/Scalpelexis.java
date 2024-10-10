package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
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

    ScalpelexisEffect() {
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
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        while (player.getLibrary().hasCards()) {
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
            player.moveCards(cards, Zone.EXILED, source, game);
            if (!CardUtil.checkAnyPairs(cards.getCards(game), (c1, c2) -> c1.sharesName(c2, game))) {
                break;
            }
        }
        return true;
    }
}
