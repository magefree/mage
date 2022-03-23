
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author djbrez
 */
public final class ChimneyImp extends CardImpl {

    public ChimneyImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Chimney Imp dies, target opponent puts a card from their hand on top of their library.
        Ability ability = new DiesSourceTriggeredAbility(new ChimneyImpEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        
    }

    private ChimneyImp(final ChimneyImp card) {
        super(card);
    }

    @Override
    public ChimneyImp copy() {
        return new ChimneyImp(this);
    }
}

class ChimneyImpEffect extends OneShotEffect {

    public ChimneyImpEffect() {
        super(Outcome.Detriment);
        this.staticText = "target opponent puts a card from their hand on top of their library.";
    }

    public ChimneyImpEffect(final ChimneyImpEffect effect) {
        super(effect);
    }

    @Override
    public ChimneyImpEffect copy() {
        return new ChimneyImpEffect(this);
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
