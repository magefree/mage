
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class HelmOfObedience extends CardImpl {

    public HelmOfObedience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {X}, {T}: Target opponent puts cards from the top of their library into their graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice Helm of Obedience and put that card onto the battlefield under your control. X can't be 0.
        VariableManaCost xCosts = new VariableManaCost();
        xCosts.setMinX(1);
        SimpleActivatedAbility abilitiy = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HelmOfObedienceEffect(), xCosts);
        abilitiy.addCost(new TapSourceCost());
        abilitiy.addTarget(new TargetOpponent());
        this.addAbility(abilitiy);
    }

    public HelmOfObedience(final HelmOfObedience card) {
        super(card);
    }

    @Override
    public HelmOfObedience copy() {
        return new HelmOfObedience(this);
    }
}

class HelmOfObedienceEffect extends OneShotEffect {

    private static final ManacostVariableValue amount = ManacostVariableValue.instance;

    public HelmOfObedienceEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent puts cards from the top of their library into their graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice {this} and put that card onto the battlefield under your control. X can't be 0";
    }

    public HelmOfObedienceEffect(final HelmOfObedienceEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfObedienceEffect copy() {
        return new HelmOfObedienceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        int max = amount.calculate(game, source, this);
        if (targetOpponent != null && controller != null && max > 0) {
            int numberOfCard = 0;
            for (Card card : targetOpponent.getLibrary().getCards(game)) {
                if (card != null) {
                    if (targetOpponent.moveCards(card, Zone.GRAVEYARD, source, game)) {
                        if (card.isCreature()) {
                            // If a creature card is put into that graveyard this way, sacrifice Helm of Obedience
                            // and put that card onto the battlefield under your control.
                            Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
                            if (sourcePermanent != null) {
                                sourcePermanent.sacrifice(source.getSourceId(), game);
                            }
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            break;
                        } else {
                            numberOfCard++;
                            if (numberOfCard >= max) {
                                break;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
