package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AstralConfrontation extends CardImpl {

    private static final Hint hint = new ValueHint("Opponents you're attacking", AstralConfrontationValue.instance);

    public AstralConfrontation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // This spell costs {1} less to cast for each opponent you're attacking.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, AstralConfrontationValue.instance)
        ).setRuleAtTheTop(true));

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AstralConfrontation(final AstralConfrontation card) {
        super(card);
    }

    @Override
    public AstralConfrontation copy() {
        return new AstralConfrontation(this);
    }
}

enum AstralConfrontationValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Set<UUID> opponents = game.getOpponents(sourceAbility.getControllerId());
        return game
                .getCombat()
                .getGroups()
                .stream()
                .filter(combatGroup -> combatGroup
                        .getAttackers()
                        .stream()
                        .map(game::getControllerId)
                        .anyMatch(sourceAbility::isControlledBy)
                )
                .map(CombatGroup::getDefenderId)
                .filter(Objects::nonNull)
                .distinct()
                .filter(opponents::contains)
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public AstralConfrontationValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "opponent you're attacking";
    }

    @Override
    public String toString() {
        return "1";
    }
}
