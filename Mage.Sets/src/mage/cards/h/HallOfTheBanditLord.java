package mage.cards.h;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.PayLifeCost;
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
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class HallOfTheBanditLord extends CardImpl {

    public HallOfTheBanditLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // Hall of the Bandit Lord enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}, Pay 3 life: Add {C}. If that mana is spent on a creature spell, it gains haste.
        SimpleManaAbility ability = new SimpleManaAbility(
                new BasicManaEffect(Mana.ColorlessMana(1)), new TapSourceCost());
        ability.addEffect(new ManaSpentOnSpellGainsAbilityEffect(StaticFilters.FILTER_SPELL_CREATURE,
                new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom, null, true)));
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    private HallOfTheBanditLord(final HallOfTheBanditLord card) {
        super(card);
    }

    @Override
    public HallOfTheBanditLord copy() {
        return new HallOfTheBanditLord(this);
    }
}
