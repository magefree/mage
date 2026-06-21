package mage.cards.g;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaSpentOnSpellGainsAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class GeneratorServant extends CardImpl {

    public GeneratorServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}, Sacrifice Generator Servant: Add {C}{C}. If that mana is spent on a creature spell, it gains haste until end of turn.
        SimpleManaAbility ability = new SimpleManaAbility(
                new BasicManaEffect(Mana.ColorlessMana(2)), new TapSourceCost());
        ability.addEffect(new ManaSpentOnSpellGainsAbilityEffect(StaticFilters.FILTER_SPELL_CREATURE,
                new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, null, true)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private GeneratorServant(final GeneratorServant card) {
        super(card);
    }

    @Override
    public GeneratorServant copy() {
        return new GeneratorServant(this);
    }
}
