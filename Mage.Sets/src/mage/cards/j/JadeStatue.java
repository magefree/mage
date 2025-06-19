package mage.cards.j;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class JadeStatue extends CardImpl {

    private static final Condition condition = new IsPhaseCondition(TurnPhase.COMBAT);

    public JadeStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}: Jade Statue becomes a 3/6 Golem artifact creature until end of combat. Activate this ability only during combat.
        this.addAbility(new ActivateIfConditionActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(3, 6, "3/6 Golem artifact creature", SubType.GOLEM)
                        .withType(CardType.ARTIFACT), CardType.ARTIFACT, Duration.EndOfCombat
        ), new GenericManaCost(2), condition));
    }

    private JadeStatue(final JadeStatue card) {
        super(card);
    }

    @Override
    public JadeStatue copy() {
        return new JadeStatue(this);
    }
}
