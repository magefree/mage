
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LoneFox

 */
public final class Tek extends CardImpl {

    private static final FilterControlledPermanent filterPlains = new FilterControlledPermanent("Plains");
    private static final FilterControlledPermanent filterIsland = new FilterControlledPermanent("Island");
    private static final FilterControlledPermanent filterSwamp = new FilterControlledPermanent("Swamp");
    private static final FilterControlledPermanent filterMountain = new FilterControlledPermanent("Mountain");
    private static final FilterControlledPermanent filterForest = new FilterControlledPermanent("Forest");

    static {
        filterPlains.add(SubType.PLAINS.getPredicate());
        filterIsland.add(SubType.ISLAND.getPredicate());
        filterSwamp.add(SubType.SWAMP.getPredicate());
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
        filterForest.add(SubType.FOREST.getPredicate());
    }


    public Tek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tek gets +0/+2 as long as you control a Plains, has flying as long as you control an Island, gets +2/+0 as long as you control a Swamp, has first strike as long as you control a Mountain, and has trample as long as you control a Forest.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostSourceEffect(0, 2, Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterPlains), "{this} gets +0/+2 as long as you control a Plains"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterIsland), ", has flying as long as you control an Island"));
        ability.addEffect(new ConditionalContinuousEffect(new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterSwamp), ", gets +2/+0 as long as you control a Swamp"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterMountain), ", has first strike as long as you control a Mountain"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterForest), ", and has trample as long as you control a Forest."));
        this.addAbility(ability);
    }

    private Tek(final Tek card) {
        super(card);
    }

    @Override
    public Tek copy() {
        return new Tek(this);
    }
}
