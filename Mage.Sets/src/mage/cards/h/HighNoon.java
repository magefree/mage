package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HighNoon extends CardImpl {

    public HighNoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Each player can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(new CantCastMoreThanOneSpellEffect(TargetController.ANY)));

        // {4}{R}, Sacrifice High Noon: It deals 5 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(5, "it"), new ManaCostsImpl<>("{4}{R}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HighNoon(final HighNoon card) {
        super(card);
    }

    @Override
    public HighNoon copy() {
        return new HighNoon(this);
    }
}
