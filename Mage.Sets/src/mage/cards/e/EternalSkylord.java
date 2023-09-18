package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class EternalSkylord extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.ZOMBIE, "Zombie tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public EternalSkylord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Eternal Skylord enters the batttlefield, amass 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(2, SubType.ZOMBIE)));

        // Zombie tokens you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private EternalSkylord(final EternalSkylord card) {
        super(card);
    }

    @Override
    public EternalSkylord copy() {
        return new EternalSkylord(this);
    }
}
