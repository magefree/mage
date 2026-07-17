package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.BrainstormEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarmonizedTrio extends PrepareCard {

    public HarmonizedTrio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}", "Brainstorm", new CardType[]{CardType.INSTANT}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}, Tap two untapped creatures you control: This creature becomes prepared.
        Ability ability = new SimpleActivatedAbility(new BecomePreparedSourceEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));
        this.addAbility(ability);

        // Brainstorm
        // Instant {U}
        // Draw three cards, then put two cards from your hand on top of your library in any order.
        this.getSpellCard().getSpellAbility().addEffect(new BrainstormEffect());
    }

    private HarmonizedTrio(final HarmonizedTrio card) {
        super(card);
    }

    @Override
    public HarmonizedTrio copy() {
        return new HarmonizedTrio(this);
    }
}
