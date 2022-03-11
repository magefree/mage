

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class BloodriteInvoker extends CardImpl {

    public BloodriteInvoker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(3), new GenericManaCost(8));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public BloodriteInvoker (final BloodriteInvoker card) {
        super(card);
    }

    @Override
    public BloodriteInvoker copy() {
        return new BloodriteInvoker(this);
    }

}
