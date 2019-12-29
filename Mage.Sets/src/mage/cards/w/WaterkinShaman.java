package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterkinShaman extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WaterkinShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a creature with flying enters the battlefield under your control, Waterkin Shaman gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter
        ));
    }

    private WaterkinShaman(final WaterkinShaman card) {
        super(card);
    }

    @Override
    public WaterkinShaman copy() {
        return new WaterkinShaman(this);
    }
}
