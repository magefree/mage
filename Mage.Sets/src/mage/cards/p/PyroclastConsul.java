
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class PyroclastConsul extends CardImpl {

    public PyroclastConsul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Pyroclast Consul, you may reveal it. 
        // If you do, Pyroclast Consul deals 2 damage to each creature.        
        this.addAbility(new KinshipAbility(new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE)));
    }

    private PyroclastConsul(final PyroclastConsul card) {
        super(card);
    }

    @Override
    public PyroclastConsul copy() {
        return new PyroclastConsul(this);
    }
}
