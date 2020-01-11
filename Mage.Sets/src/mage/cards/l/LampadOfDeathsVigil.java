package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
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
public final class LampadOfDeathsVigil extends CardImpl {

    public LampadOfDeathsVigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice a creature: Each opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(1), new GenericManaCost(1));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));
        this.addAbility(ability);
    }

    private LampadOfDeathsVigil(final LampadOfDeathsVigil card) {
        super(card);
    }

    @Override
    public LampadOfDeathsVigil copy() {
        return new LampadOfDeathsVigil(this);
    }
}
