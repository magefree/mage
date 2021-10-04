
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.HumanToken;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward
 */
public final class IncreasingDevotion extends CardImpl {

    public IncreasingDevotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Create five 1/1 white Human creature tokens. If this spell was cast from a graveyard, create ten of those tokens instead.
        this.getSpellAbility().addEffect(new IncreasingDevotionEffect());

        // Flashback {7}{W}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{7}{W}{W}")));
    }

    private IncreasingDevotion(final IncreasingDevotion card) {
        super(card);
    }

    @Override
    public IncreasingDevotion copy() {
        return new IncreasingDevotion(this);
    }
}

class IncreasingDevotionEffect extends OneShotEffect {

    private static HumanToken token = new HumanToken();

    public IncreasingDevotionEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create five 1/1 white Human creature tokens. If this spell was cast from a graveyard, create ten of those tokens instead";
    }

    public IncreasingDevotionEffect(final IncreasingDevotionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 5;
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (spell != null) {
            if (spell.getFromZone() == Zone.GRAVEYARD) {
                amount = 10;
            }
            token.putOntoBattlefield(amount, game, source, source.getControllerId());
            return true;
        }
        return false;
    }

    @Override
    public IncreasingDevotionEffect copy() {
        return new IncreasingDevotionEffect(this);
    }

}
