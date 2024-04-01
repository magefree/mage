package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticalTether extends CardImpl {

    public MysticalTether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // You may cast Mystical Tether as though it had flash if you pay {2} more to cast it.
        this.addAbility(new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}{W}")).setRuleAtTheTop(true));

        // When Mystical Tether enters the battlefield, exile target artifact or creature an opponent controls until Mystical Tether leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private MysticalTether(final MysticalTether card) {
        super(card);
    }

    @Override
    public MysticalTether copy() {
        return new MysticalTether(this);
    }
}
