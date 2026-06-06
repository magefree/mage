package mage.cards.k;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarvanistaLoyalLupari extends AdventureCard {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HUMAN);

    public KarvanistaLoyalLupari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ALIEN, SubType.DOG, SubType.SOLDIER}, "{4}{G}",
                "Lupari Shield",
                new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Karvanista, Loyal Lupari
        this.getLeftHalfCard().setPT(5, 5);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever Karvanista attacks, put a +1/+1 counter on each Human you control.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)));

        // Lupari Shield
        // Humans you control gain indestructible until your next turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn, filter, false
        ).setText("Humans you control gain indestructible until your next turn"));

        finalizeCard();
    }

    private KarvanistaLoyalLupari(final KarvanistaLoyalLupari card) {
        super(card);
    }

    @Override
    public KarvanistaLoyalLupari copy() {
        return new KarvanistaLoyalLupari(this);
    }
}
