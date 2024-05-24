package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThunderSalvo extends CardImpl {

    public ThunderSalvo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Thunder Salvo deals X damage to target creature, where X is 2 plus the number of other spells you've cast this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new IntPlusDynamicValue(2, ThunderSalvoValue.instance))
                .setText("{this} deals X damage to target creature, where X is 2 plus the number of other spells you've cast this turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(new ValueHint("Number of other spells you've cast this turn", ThunderSalvoValue.instance));
    }

    private ThunderSalvo(final ThunderSalvo card) {
        super(card);
    }

    @Override
    public ThunderSalvo copy() {
        return new ThunderSalvo(this);
    }
}

enum ThunderSalvoValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return 0;
        }
        Spell spell = game.getSpell(sourceAbility.getSourceId());
        return watcher.getSpellsCastThisTurn(sourceAbility.getControllerId())
                .stream()
                .filter(s -> spell == null || !spell.getId().equals(s.getId()))
                .mapToInt(k -> 1)
                .sum();
    }

    @Override
    public ThunderSalvoValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "Number of other spells you've cast this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}