package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpassionedOrator extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ImpassionedOrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));
    }

    private ImpassionedOrator(final ImpassionedOrator card) {
        super(card);
    }

    @Override
    public ImpassionedOrator copy() {
        return new ImpassionedOrator(this);
    }
}
