
package mage.cards.o;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class OverrideCard extends CardImpl {

    public OverrideCard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell unless its controller pays {1} for each artifact you control.
        this.getSpellAbility().addEffect(new OverrideEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public OverrideCard(final OverrideCard card) {
        super(card);
    }

    @Override
    public OverrideCard copy() {
        return new OverrideCard(this);
    }
}

class OverrideEffect extends OneShotEffect {

    public OverrideEffect() {
        super(Outcome.Benefit);
    }

    public OverrideEffect(final OverrideEffect effect) {
        super(effect);
    }

    @Override
    public OverrideEffect copy() {
        return new OverrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            if (player != null && controller != null) {
                int amount = game.getBattlefield().countAll(new FilterArtifactPermanent(), source.getControllerId(), game);
                if (amount > 0) {
                    GenericManaCost cost = new GenericManaCost(amount);
                    if (!cost.pay(source, game, spell.getControllerId(), spell.getControllerId(), false)) {
                        game.informPlayers(sourceObject.getLogName() + ": cost wasn't payed - countering target spell.");
                        return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {1} for each artifact you control";
    }

}
