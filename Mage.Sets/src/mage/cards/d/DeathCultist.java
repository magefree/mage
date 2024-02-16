
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
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
 * @author North
 */
public final class DeathCultist extends CardImpl {

    public DeathCultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new LoseLifeTargetEffect(1),
                new SacrificeSourceCost());
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DeathCultist(final DeathCultist card) {
        super(card);
    }

    @Override
    public DeathCultist copy() {
        return new DeathCultist(this);
    }
}