
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DevouredCreaturesCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DevourEffect.DevourFactor;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class HellkiteHatchling extends CardImpl {

    public HellkiteHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devour 1 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(DevourFactor.Devour1));

        // Hellkite Hatchling has flying and trample if it devoured a creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect( 
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                new DevouredCreaturesCondition(ComparisonType.MORE_THAN, 0),
                "{this} has flying if it devoured a creature")));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                new DevouredCreaturesCondition(ComparisonType.MORE_THAN, 0),
                "{this} has trample if it devoured a creature")));
    }

    private HellkiteHatchling(final HellkiteHatchling card) {
        super(card);
    }

    @Override
    public HellkiteHatchling copy() {
        return new HellkiteHatchling(this);
    }
}
