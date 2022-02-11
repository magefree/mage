
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.OutlastAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class LongshotSquad extends CardImpl {

    public LongshotSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Outlast 1G
        this.addAbility(new OutlastAbility(new ManaCostsImpl("{1}{G}")));
        // Each creature you control with a +1/+1 counter on it has reach.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        ReachAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1)
                )
        );
    }

    private LongshotSquad(final LongshotSquad card) {
        super(card);
    }

    @Override
    public LongshotSquad copy() {
        return new LongshotSquad(this);
    }
}
