
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class MerEkNightblade extends CardImpl {

    public MerEkNightblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Outlast {B}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{B}")));
        
        // Each creature you control with a +1/+1 counter on it has deathtouch.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        DeathtouchAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1)
                )
        );
    }

    private MerEkNightblade(final MerEkNightblade card) {
        super(card);
    }

    @Override
    public MerEkNightblade copy() {
        return new MerEkNightblade(this);
    }
}
