
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GiantTortoise extends CardImpl {

    public GiantTortoise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.TURTLE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Giant Tortoise gets +0/+3 as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(0,3, Duration.WhileOnBattlefield),
                SourceTappedCondition.UNTAPPED,
                "{this} gets +0/+3 as long as it's untapped")));
    }

    private GiantTortoise(final GiantTortoise card) {
        super(card);
    }

    @Override
    public GiantTortoise copy() {
        return new GiantTortoise(this);
    }
}
