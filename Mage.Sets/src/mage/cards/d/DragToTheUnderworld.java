package mage.cards.d;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragToTheUnderworld extends CardImpl {

    public DragToTheUnderworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // This spell costs {X} less to cast, where X is your devotion to black.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new DragToTheUnderworldEffect())
                .addHint(DevotionCount.B.getHint())
                .setRuleAtTheTop(true));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DragToTheUnderworld(final DragToTheUnderworld card) {
        super(card);
    }

    @Override
    public DragToTheUnderworld copy() {
        return new DragToTheUnderworld(this);
    }
}

class DragToTheUnderworldEffect extends CostModificationEffectImpl {

    DragToTheUnderworldEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is your devotion to black. " +
                "<i>(Each {B} in the mana costs of permanents you control counts toward your devotion to black.)</i> ";
    }

    private DragToTheUnderworldEffect(final DragToTheUnderworldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() == 0) {
            return false;
        }
        int count = DevotionCount.B.calculate(game, source, this);
        mana.setGeneric(Math.max(mana.getGeneric() - count, 0));
        spellAbility.getManaCostsToPay().load(mana.toString());
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public DragToTheUnderworldEffect copy() {
        return new DragToTheUnderworldEffect(this);
    }
}
