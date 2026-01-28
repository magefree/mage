package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class CaptainAmericaUnbowed extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Soldiers and Heroes you control");

    static {
        filter.add(Predicates.or(
            SubType.SOLDIER.getPredicate(),
            SubType.HERO.getPredicate()
        ));
    }

    public CaptainAmericaUnbowed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Captain America enters, Soldiers and Heroes you control gain indestructible until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter)
        ));
    }

    private CaptainAmericaUnbowed(final CaptainAmericaUnbowed card) {
        super(card);
    }

    @Override
    public CaptainAmericaUnbowed copy() {
        return new CaptainAmericaUnbowed(this);
    }
}
