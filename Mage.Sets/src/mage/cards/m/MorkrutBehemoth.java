package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorkrutBehemoth extends CardImpl {

    public MorkrutBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // As an additional cost to cast this spell, sacrifice a creature or pay {1}{B}.
        this.getSpellAbility().addCost(new OrCost(
                new SacrificeTargetCost(new TargetControlledPermanent(
                        StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                )), new ManaCostsImpl<>("{1}{B}"), "sacrifice a creature or pay {1}{B}"
        ));

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private MorkrutBehemoth(final MorkrutBehemoth card) {
        super(card);
    }

    @Override
    public MorkrutBehemoth copy() {
        return new MorkrutBehemoth(this);
    }
}
