
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class CliffrunnerBehemoth extends CardImpl {
    private static final FilterPermanent redPermanentFilter = new FilterPermanent("red");
    private static final FilterPermanent whitePermanentFilter = new FilterPermanent("white");

    static {
        redPermanentFilter.add(new ColorPredicate(ObjectColor.RED));
        whitePermanentFilter.add(new ColorPredicate(ObjectColor.WHITE));
    }


    public CliffrunnerBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Cliffrunner Behemoth has haste as long as you control a red permanent.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield),
                    new PermanentsOnTheBattlefieldCondition(redPermanentFilter), "{this} has haste as long as you control a red permanent")));
        // Cliffrunner Behemoth has lifelink as long as you control a white permanent.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield),
                    new PermanentsOnTheBattlefieldCondition(whitePermanentFilter), "{this} has lifelink as long as you control a white permanent")));
    }

    private CliffrunnerBehemoth(final CliffrunnerBehemoth card) {
        super(card);
    }

    @Override
    public CliffrunnerBehemoth copy() {
        return new CliffrunnerBehemoth(this);
    }
}
