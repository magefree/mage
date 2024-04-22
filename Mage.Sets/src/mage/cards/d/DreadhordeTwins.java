package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadhordeTwins extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "Zombie tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public DreadhordeTwins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Dreadhorde Twins enters the battlefield, amass 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(2, SubType.ZOMBIE)));

        // Zombie tokens you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private DreadhordeTwins(final DreadhordeTwins card) {
        super(card);
    }

    @Override
    public DreadhordeTwins copy() {
        return new DreadhordeTwins(this);
    }
}
