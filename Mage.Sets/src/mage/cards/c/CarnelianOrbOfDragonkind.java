package mage.cards.c;

import mage.Mana;
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
import mage.filter.common.FilterCreatureSpell;

import java.util.UUID;

/**
 *
 * @author AmeyMirchandani
 */
public final class CarnelianOrbOfDragonkind extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("a Dragon creature spell");
    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public CarnelianOrbOfDragonkind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // {T}: Add {R}. If that mana is spent on a Dragon creature spell, it gains haste until end of turn.
        SimpleManaAbility ability = new SimpleManaAbility(
                new BasicManaEffect(Mana.RedMana(1)), new TapSourceCost());
        ability.addEffect(new ManaSpentOnSpellGainsAbilityEffect(filter,
                new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, null, true)));
        this.addAbility(ability);
    }

    private CarnelianOrbOfDragonkind(final CarnelianOrbOfDragonkind card) {
        super(card);
    }

    @Override
    public CarnelianOrbOfDragonkind copy() {
        return new CarnelianOrbOfDragonkind(this);
    }
}
