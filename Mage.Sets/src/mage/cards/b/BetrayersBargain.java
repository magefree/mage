package mage.cards.b;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BetrayersBargain extends CardImpl {

    public BetrayersBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // As an additional cost to cast this spell, sacrifice a creature or enchantment or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice a creature or enchantment or pay {2}",
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT), new GenericManaCost(2)
        ));

        // Betrayer's Bargain deals 5 damage to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BetrayersBargain(final BetrayersBargain card) {
        super(card);
    }

    @Override
    public BetrayersBargain copy() {
        return new BetrayersBargain(this);
    }
}
