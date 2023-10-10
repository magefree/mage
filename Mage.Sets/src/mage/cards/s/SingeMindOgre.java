
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class SingeMindOgre extends CardImpl {

    public SingeMindOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Singe-Mind Ogre enters the battlefield, target player reveals a card at random from their hand, then loses life equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SingeMindOgreEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private SingeMindOgre(final SingeMindOgre card) {
        super(card);
    }

    @Override
    public SingeMindOgre copy() {
        return new SingeMindOgre(this);
    }
}

class SingeMindOgreEffect extends OneShotEffect {

    public SingeMindOgreEffect() {
        super(Outcome.LoseLife);
        this.staticText = "target player reveals a card at random from their hand, then loses life equal to that card's mana value";
    }

    private SingeMindOgreEffect(final SingeMindOgreEffect effect) {
        super(effect);
    }

    @Override
    public SingeMindOgreEffect copy() {
        return new SingeMindOgreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null && !targetPlayer.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            Card card = targetPlayer.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                targetPlayer.revealCards("Singe-Mind Ogre", revealed, game);
                targetPlayer.loseLife(card.getManaValue(), game, source, false);
                return true;
            }
        }
        return false;
    }
}
