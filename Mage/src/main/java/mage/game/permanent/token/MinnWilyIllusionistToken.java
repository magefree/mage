package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class MinnWilyIllusionistToken extends TokenImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ILLUSION);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public MinnWilyIllusionistToken() {
        super("Illusion", "1/1 blue Illusion creature token with \"This creature gets +1/+0 for each other Illusion you control.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("this creature gets +1/+0 for each other Illusion you control")));

        availableImageSetCodes = Arrays.asList("AFC");
    }

    public MinnWilyIllusionistToken(final MinnWilyIllusionistToken token) {
        super(token);
    }

    public MinnWilyIllusionistToken copy() {
        return new MinnWilyIllusionistToken(this);
    }
}
