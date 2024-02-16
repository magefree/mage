
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MountedArchers extends CardImpl {

    public MountedArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        
        // {W}: Mounted Archers can block an additional creature this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{W}")));
    }

    private MountedArchers(final MountedArchers card) {
        super(card);
    }

    @Override
    public MountedArchers copy() {
        return new MountedArchers(this);
    }
}
