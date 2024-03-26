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
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new RippleAbility(4), StaticFilters.FILTER_CARD_NON_LAND)
                .setText("Spells you cast have ripple 4. <i>(Whenever you cast a spell, you may reveal the top four cards " +
                        "of your library. You may cast spells with the same name as that spell from among the revealed " +
                        "cards without paying their mana costs. Put the rest on the bottom of your library.)</i>")));
    }

    private ThrummingStone(final ThrummingStone card) {
        super(card);
    }

    @Override
    public ThrummingStone copy() {
        return new ThrummingStone(this);
    }
}
