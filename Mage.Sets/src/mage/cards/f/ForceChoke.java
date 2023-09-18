package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author Styxo
 */
public final class ForceChoke extends CardImpl {

    public ForceChoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{B}");

        // Counter target spell. Its controller may pay life equal to that 
        // spell's cmc to return it to its owner's hand.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ForceChokeEffect());

        // Scry 2
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private ForceChoke(final ForceChoke card) {
        super(card);
    }

    @Override
    public ForceChoke copy() {
        return new ForceChoke(this);
    }
}

class ForceChokeEffect extends OneShotEffect {

    public ForceChokeEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Counter target spell. Its controller may pay life "
                + "equal to that spell's mana value to return it to its owner's hand";
    }

    private ForceChokeEffect(final ForceChokeEffect effect) {
        super(effect);
    }

    @Override
    public ForceChokeEffect copy() {
        return new ForceChokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        StackObject stackObject = (StackObject) game.getObject(getTargetPointer().getFirst(game, source));
        Player objectController = game.getPlayer(stackObject.getControllerId());
        if (player != null) {
            Cost cost = new PayLifeCost(stackObject.getManaValue());
            if (cost.canPay(source, source, objectController.getId(), game)
                    && objectController.chooseUse(Outcome.LoseLife, "Pay "
                            + stackObject.getManaValue() + " life?", source, game)
                    && cost.pay(source, game, source, objectController.getId(), false, null)) {
                objectController.moveCards((Card) stackObject, Zone.HAND, source, game);
            } else {
                game.getStack().counter(stackObject.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}
