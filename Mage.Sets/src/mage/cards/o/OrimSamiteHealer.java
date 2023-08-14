
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class OrimSamiteHealer extends CardImpl {

    public OrimSamiteHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 3), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // {tap}: Prevent the next 3 damage that would be dealt to any target this turn.
    }

    private OrimSamiteHealer(final OrimSamiteHealer card) {
        super(card);
    }

    @Override
    public OrimSamiteHealer copy() {
        return new OrimSamiteHealer(this);
    }
}
