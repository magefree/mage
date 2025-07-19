package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunsetStrikemaster extends CardImpl {

    public SunsetStrikemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {2}{R}, {T}, Sacrifice this creature: It deals 6 damage to target creature with flying.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(6, "it"), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private SunsetStrikemaster(final SunsetStrikemaster card) {
        super(card);
    }

    @Override
    public SunsetStrikemaster copy() {
        return new SunsetStrikemaster(this);
    }
}
