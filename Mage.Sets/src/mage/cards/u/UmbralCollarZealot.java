package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UmbralCollarZealot extends CardImpl {

    public UmbralCollarZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Sacrifice another creature or artifact: Surveil 1.
        this.addAbility(new SimpleActivatedAbility(
                new SurveilEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
        ));
    }

    private UmbralCollarZealot(final UmbralCollarZealot card) {
        super(card);
    }

    @Override
    public UmbralCollarZealot copy() {
        return new UmbralCollarZealot(this);
    }
}
