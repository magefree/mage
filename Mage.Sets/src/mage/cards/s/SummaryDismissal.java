
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public final class SummaryDismissal extends CardImpl {

    public SummaryDismissal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Exile all other spells and counter all abilities.
        this.getSpellAbility().addEffect(new SummaryDismissalEffect());
    }

    private SummaryDismissal(final SummaryDismissal card) {
        super(card);
    }

    @Override
    public SummaryDismissal copy() {
        return new SummaryDismissal(this);
    }
}

class SummaryDismissalEffect extends OneShotEffect {

    public SummaryDismissalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all other spells and counter all abilities";
    }

    private SummaryDismissalEffect(final SummaryDismissalEffect effect) {
        super(effect);
    }

    @Override
    public SummaryDismissalEffect copy() {
        return new SummaryDismissalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<StackObject> stackList = new ArrayList<>();
        for (StackObject stackObject : game.getStack()) {
            if (!stackObject.getSourceId().equals(source.getSourceId())) {
                stackList.add(stackObject);
            }
        }
        for (StackObject stackObject : stackList) {
            if (stackObject instanceof Spell) {
                ((Spell) stackObject).moveToExile(null, "", null, game);
            }
            if (stackObject instanceof Ability) {
                game.getStack().counter(stackObject.getId(), source, game);
            }
        }
        return true;
    }
}
