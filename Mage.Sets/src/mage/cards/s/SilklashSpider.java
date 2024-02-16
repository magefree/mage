
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author North
 */
public final class SilklashSpider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SilklashSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {X}{G}{G}: Silklash Spider deals X damage to each creature with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageAllEffect(ManacostVariableValue.REGULAR, filter),
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
