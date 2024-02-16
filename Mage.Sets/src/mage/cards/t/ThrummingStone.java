package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.RippleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author klayhamn
 */
public final class ThrummingStone extends CardImpl {

    public ThrummingStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.supertype.add(SuperType.LEGENDARY);

        // Spells you cast have ripple 4.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new RippleAbility(4), StaticFilters.FILTER_CARD)
                .setText("spells you cast have ripple 4")));
    }

    private ThrummingStone(final ThrummingStone card) {
        super(card);
    }

    @Override
    public ThrummingStone copy() {
        return new ThrummingStone(this);
    }
}
