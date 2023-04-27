package mage.cards.f;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FolkMedicine extends CardImpl {

    public FolkMedicine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // You gain 1 life for each creature you control.
        DynamicValue amount = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addEffect(new GainLifeEffect(amount));
        // Flashback {1}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{W}")));
    }

    private FolkMedicine(final FolkMedicine card) {
        super(card);
    }

    @Override
    public FolkMedicine copy() {
        return new FolkMedicine(this);
    }
}
