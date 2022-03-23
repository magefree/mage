
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class ChitteringRats extends CardImpl {

    public ChitteringRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Chittering Rats enters the battlefield, target opponent puts a card from their hand on top of their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ChitteringRatsEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private ChitteringRats(final ChitteringRats card) {
        super(card);
    }

    @Override
    public ChitteringRats copy() {
        return new ChitteringRats(this);
    }
}

class ChitteringRatsEffect extends OneShotEffect {

    public ChitteringRatsEffect() {
        super(Outcome.Detriment);
        this.staticText = "target opponent puts a card from their hand on top of their library";
    }

    public ChitteringRatsEffect(final ChitteringRatsEffect effect) {
        super(effect);
    }

    @Override
    public ChitteringRatsEffect copy() {
        return new ChitteringRatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetOpponent != null) {
            if (!targetOpponent.getHand().isEmpty()) {
                TargetCardInHand target = new TargetCardInHand();
                target.setNotTarget(true);
                target.setTargetName("a card from your hand to put on top of your library");
                targetOpponent.choose(Outcome.Detriment, target, source, game);
                Card card = targetOpponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    targetOpponent.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
