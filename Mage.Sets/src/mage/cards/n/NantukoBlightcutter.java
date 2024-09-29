package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class NantukoBlightcutter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public NantukoBlightcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // Threshold - Nantuko Blightcutter gets +1/+1 for each black permanent your opponents control as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield),
                ThresholdCondition.instance, "{this} gets +1/+1 for each black permanent " +
                "your opponents control as long as seven or more cards are in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private NantukoBlightcutter(final NantukoBlightcutter card) {
        super(card);
    }

    @Override
    public NantukoBlightcutter copy() {
        return new NantukoBlightcutter(this);
    }
}
