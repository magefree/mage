package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpitefulSliver extends CardImpl {

    public SpitefulSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have "Whenever this creature is dealt damage, it deals that much damage to target player or planeswalker."
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new SpitefulSliverEffect(),
                false, false
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_SLIVERS
        ).setText("Sliver creatures you control have \"Whenever this creature is dealt damage, " +
                "it deals that much damage to target player or planeswalker.\"")
        ));
    }

    private SpitefulSliver(final SpitefulSliver card) {
        super(card);
    }

    @Override
    public SpitefulSliver copy() {
        return new SpitefulSliver(this);
    }
}

class SpitefulSliverEffect extends OneShotEffect {

    SpitefulSliverEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to target player or planeswalker";
    }

    private SpitefulSliverEffect(final SpitefulSliverEffect effect) {
        super(effect);
    }

    @Override
    public SpitefulSliverEffect copy() {
        return new SpitefulSliverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        return new DamageTargetEffect(amount).apply(game, source);
    }
}
