
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class ScepterOfFugue extends CardImpl {

    public ScepterOfFugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{B}{B}");


        // {1}{B}, {tap}: Target player discards a card. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl("{1}{B}"), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public ScepterOfFugue(final ScepterOfFugue card) {
        super(card);
    }

    @Override
    public ScepterOfFugue copy() {
        return new ScepterOfFugue(this);
    }
}
