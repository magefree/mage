
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class AlabasterWall extends CardImpl {

    public AlabasterWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // {tap}: Prevent the next 1 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private AlabasterWall(final AlabasterWall card) {
        super(card);
    }

    @Override
    public AlabasterWall copy() {
        return new AlabasterWall(this);
    }
}
