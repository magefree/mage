
package mage.cards.j;

import mage.MageInt;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author anonymous
 */
public final class JadeStatue extends CardImpl {

    public JadeStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        

        // {2}: Jade Statue becomes a 3/6 Golem artifact creature until end of combat. Activate this ability only during combat.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect (new JadeStatueToken(), "", Duration.EndOfCombat), new ManaCostsImpl<>("{2}"), new IsPhaseCondition(TurnPhase.COMBAT), "{2}: {this} becomes a 3/6 Golem artifact creature until end of combat. Activate only during combat."));
    }

    private JadeStatue(final JadeStatue card) {
        super(card);
    }

    @Override
    public JadeStatue copy() {
        return new JadeStatue(this);
    }
    
    private static class JadeStatueToken extends TokenImpl {
        JadeStatueToken() {
            super("", "3/6 Golem artifact creature");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            this.subtype.add(SubType.GOLEM);
            power = new MageInt(3);
            toughness = new MageInt(6);
	    }
        public JadeStatueToken(final JadeStatueToken token) {
            super(token);
        }

        public JadeStatueToken copy() {
            return new JadeStatueToken(this);
        }
    }
}
