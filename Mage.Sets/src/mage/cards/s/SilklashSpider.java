
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class SilklashSpider extends CardImpl {

    public SilklashSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {X}{G}{G}: Silklash Spider deals X damage to each creature with flying.
        this.addAbility(new SimpleActivatedAbility(
                new DamageAllEffect(GetXValue.instance, StaticFilters.FILTER_CREATURE_FLYING),
                new ManaCostsImpl<>("{X}{G}{G}")));
    }

    private SilklashSpider(final SilklashSpider card) {
        super(card);
    }

    @Override
    public SilklashSpider copy() {
        return new SilklashSpider(this);
    }
}
