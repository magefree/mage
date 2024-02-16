
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class AnuridSwarmsnapper extends CardImpl {

    public AnuridSwarmsnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {1}{G}: Anurid Swarmsnapper can block an additional creature this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{1}{G}")));
    }

    private AnuridSwarmsnapper(final AnuridSwarmsnapper card) {
        super(card);
    }

    @Override
    public AnuridSwarmsnapper copy() {
        return new AnuridSwarmsnapper(this);
    }
}
