package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class HazeFrog extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HazeFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Haze Frog enters the battlefield, prevent all combat damage that other creatures would deal this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true)
                .setText("prevent all combat damage that other creatures would deal this turn")
        ));
    }

    private HazeFrog(final HazeFrog card) {
        super(card);
    }

    @Override
    public HazeFrog copy() {
        return new HazeFrog(this);
    }
}
