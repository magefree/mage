
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ArmoryGuard extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.GATE));

    public ArmoryGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Armory Guard has vigilance as long as you control a Gate.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance()),
                condition, "{this} has vigilance as long as you control a Gate"
        )));
    }

    private ArmoryGuard(final ArmoryGuard card) {
        super(card);
    }

    @Override
    public ArmoryGuard copy() {
        return new ArmoryGuard(this);
    }
}
