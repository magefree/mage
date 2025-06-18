
package mage.cards.t;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TurnPhase;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class TIEBomber extends CardImpl {

    private static final Condition condition = new IsPhaseCondition(TurnPhase.COMBAT);

    public TIEBomber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // {1}: TIE Bomber loses Spaceflight until end od turn. Activate this ability only during combat.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new LoseAbilitySourceEffect(SpaceflightAbility.getInstance(), Duration.EndOfTurn),
                new GenericManaCost(1), condition
        ));
    }

    private TIEBomber(final TIEBomber card) {
        super(card);
    }

    @Override
    public TIEBomber copy() {
        return new TIEBomber(this);
    }
}
