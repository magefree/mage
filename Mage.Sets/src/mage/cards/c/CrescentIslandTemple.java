package mage.cards.c;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.MonkRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrescentIslandTemple extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SHRINE);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Shrines you control", xValue);
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.SHRINE, "another Shrine you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CrescentIslandTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // When Crescent Island Temple enters, for each Shrine you control, create a 1/1 red Monk creature token with prowess.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MonkRedToken(), xValue)
                .setText("for each Shrine you control, create a 1/1 red Monk creature token with prowess")).addHint(hint));

        // Whenever another Shrine you control enters, create a 1/1 red Monk creature token with prowess.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new CreateTokenEffect(new MonkRedToken()), filter2));
    }

    private CrescentIslandTemple(final CrescentIslandTemple card) {
        super(card);
    }

    @Override
    public CrescentIslandTemple copy() {
        return new CrescentIslandTemple(this);
    }
}
