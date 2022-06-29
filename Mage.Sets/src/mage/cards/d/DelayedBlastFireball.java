package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DelayedBlastFireball extends CardImpl {

    public DelayedBlastFireball(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Delayed Blast Fireball deals 2 damage to each opponent and each creature they control. If this spell was cast from exile, it deals 5 damage to each opponent and each creature they control instead.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(
                Outcome.Damage, DelayedBlastFireballValue.instance, TargetController.OPPONENT
        ));
        this.getSpellAbility().addEffect(new DamageAllEffect(
                DelayedBlastFireballValue.instance, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("and each creature they control. If this spell was cast from exile, " +
                "it deals 5 damage to each opponent and each creature they control instead"));

        // Foretell {4}{R}{R}
        this.addAbility(new ForetellAbility(this, "{4}{R}{R}"));
    }

    private DelayedBlastFireball(final DelayedBlastFireball card) {
        super(card);
    }

    @Override
    public DelayedBlastFireball copy() {
        return new DelayedBlastFireball(this);
    }
}

enum DelayedBlastFireballValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(sourceAbility.getSourceObjectIfItStillExists(game))
                .filter(Spell.class::isInstance)
                .map(Spell.class::cast)
                .map(Spell::getFromZone)
                .filter(Zone.EXILED::match)
                .map(x -> 5)
                .orElse(2);
    }

    @Override
    public DelayedBlastFireballValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "2";
    }
}
