
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.RegenerateAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author dustinconrad
 */
public final class LoxodonHierarch extends CardImpl {


    public LoxodonHierarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Loxodon Hierarch enters the battlefield, you gain 4 life.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4));
        this.addAbility(etbAbility);
        // {G}{W}, Sacrifice Loxodon Hierarch: Regenerate each creature you control.
        Ability activated = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED), new ManaCostsImpl<>("{G}{W}"));
        activated.addCost(new SacrificeSourceCost());
        this.addAbility(activated);
    }

    private LoxodonHierarch(final LoxodonHierarch card) {
        super(card);
    }

    @Override
    public LoxodonHierarch copy() {
        return new LoxodonHierarch(this);
    }
}
