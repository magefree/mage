package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcolyteOfAclazotz extends CardImpl {

    public AcolyteOfAclazotz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}, Sacrifice another creature or artifact: Each opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT_SHORT_TEXT));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private AcolyteOfAclazotz(final AcolyteOfAclazotz card) {
        super(card);
    }

    @Override
    public AcolyteOfAclazotz copy() {
        return new AcolyteOfAclazotz(this);
    }
}
