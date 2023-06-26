package mage.cards.i;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class InTheTrenches extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public InTheTrenches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));

        // {5}{W}: Exile target nonland permanent you don't control until In the Trenches leaves the battlefield. Activate only as a sorcery and only once.
        Ability ability = new ActivateOncePerGameActivatedAbility(Zone.BATTLEFIELD, new ExileUntilSourceLeavesEffect(), new ManaCostsImpl<>("{5}{W}"), TimingRule.SORCERY);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private InTheTrenches(final InTheTrenches card) {
        super(card);
    }

    @Override
    public InTheTrenches copy() {
        return new InTheTrenches(this);
    }
}
