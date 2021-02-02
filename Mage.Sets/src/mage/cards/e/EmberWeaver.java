
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author Loki
 */
public final class EmberWeaver extends CardImpl {
    private static final FilterPermanent redPermanentFilter = new FilterPermanent("red");

    static {
        redPermanentFilter.add(new ColorPredicate(ObjectColor.RED));
    }

    public EmberWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());
        // As long as you control a red permanent, Ember Weaver gets +1/+0 and has first strike.
        this.addAbility(new SimpleStaticAbility(
                        Zone.BATTLEFIELD,
                        new ConditionalContinuousEffect(
                                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                                new PermanentsOnTheBattlefieldCondition(redPermanentFilter), "{this} gets +1/+0 as long as you control a red permanent")));
        this.addAbility(new SimpleStaticAbility(
                        Zone.BATTLEFIELD,
                        new ConditionalContinuousEffect(
                                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                                new PermanentsOnTheBattlefieldCondition(redPermanentFilter), "{this} has first strike as long as you control a red permanent")));

    }

    private EmberWeaver(final EmberWeaver card) {
        super(card);
    }

    @Override
    public EmberWeaver copy() {
        return new EmberWeaver(this);
    }
}
