package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author noahg
 */
public final class ThoughtDissector extends CardImpl {

    public ThoughtDissector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");


        // {X}, {tap}: Target opponent reveals cards from the top of their library until an artifact card or X cards are revealed, whichever comes first. If an artifact card is revealed this way, put it onto the battlefield under your control and sacrifice Thought Dissector. Put the rest of the revealed cards into that player's graveyard.
        SimpleActivatedAbility abilitiy = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThoughtDissectorEffect(), new VariableManaCost(VariableCostType.NORMAL));
        abilitiy.addCost(new TapSourceCost());
        abilitiy.addTarget(new TargetOpponent());
        this.addAbility(abilitiy);
    }

    private ThoughtDissector(final ThoughtDissector card) {
        super(card);
    }

    @Override
    public ThoughtDissector copy() {
        return new ThoughtDissector(this);
    }
}

class ThoughtDissectorEffect extends OneShotEffect {

    private static final ManacostVariableValue amount = ManacostVariableValue.REGULAR;

    public ThoughtDissectorEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent reveals cards from the top of their library until an artifact card or X cards are revealed, whichever comes first. If an artifact card is revealed this way, put it onto the battlefield under your control and sacrifice {this}. Put the rest of the revealed cards into that player's graveyard.";
    }

    private ThoughtDissectorEffect(final ThoughtDissectorEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtDissectorEffect copy() {
        return new ThoughtDissectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        int max = amount.calculate(game, source, this);
        if (targetOpponent != null && controller != null && max > 0) {
            int numberOfCard = 0;
            Card artifact = null;
            CardsImpl nonArtifacts = new CardsImpl();
            CardsImpl reveal = new CardsImpl();
            for (Card card : targetOpponent.getLibrary().getCards(game)) {
                reveal.add(card);
                if (card.isArtifact(game)) {
                    artifact = card;
                    break;
                } else {
                    numberOfCard++;
                    if (numberOfCard > max) {
                        break;
                    }
                    nonArtifacts.add(card);
                }
            }
            targetOpponent.revealCards(source, reveal, game);
            if (artifact != null) {
                game.getState().processAction(game);
                controller.moveCards(artifact, Zone.BATTLEFIELD, source, game);
                Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
                if (sourcePermanent != null) {
                    sourcePermanent.sacrifice(source, game);
                }
            }
            targetOpponent.moveCards(nonArtifacts, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }

}