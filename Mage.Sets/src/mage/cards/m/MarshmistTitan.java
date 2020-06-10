package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MarshmistTitan extends CardImpl {

    public MarshmistTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Marshmist Titan costs {X} less to cast, where X is your devotion to black.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MarshmistTitanCostReductionEffect())
                .addHint(DevotionCount.B.getHint()));
    }

    private MarshmistTitan(final MarshmistTitan card) {
        super(card);
    }

    @Override
    public MarshmistTitan copy() {
        return new MarshmistTitan(this);
    }
}

class MarshmistTitanCostReductionEffect extends CostModificationEffectImpl {

    MarshmistTitanCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is your devotion to black. " +
                "<i>(Each {B} in the mana costs of permanents you control counts toward your devotion to black.)</i> ";
    }

    private MarshmistTitanCostReductionEffect(final MarshmistTitanCostReductionEffect effect) {
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
    public MarshmistTitanCostReductionEffect copy() {
        return new MarshmistTitanCostReductionEffect(this);
    }
}
