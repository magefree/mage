
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class MarshmistTitan extends CardImpl {

    public MarshmistTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Marshmist Titan costs {X} less to cast, where X is your devotion to black.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new MarshmistTitanCostReductionEffect()));
    }

    public MarshmistTitan(final MarshmistTitan card) {
        super(card);
    }

    @Override
    public MarshmistTitan copy() {
        return new MarshmistTitan(this);
    }
}

class MarshmistTitanCostReductionEffect extends CostModificationEffectImpl {

    public MarshmistTitanCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {X} less to cast, where X is your devotion to black  <i>(Each {B} in the mana costs of permanents you control counts toward your devotion to black.)</i> ";
    }

    public MarshmistTitanCostReductionEffect(final MarshmistTitanCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility)abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() > 0) {
            int count = new DevotionCount(ColoredManaSymbol.B).calculate(game, source, this);
            int newCount = mana.getGeneric() - count;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setGeneric(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {
            return true;
        }
        return false;
    }

    @Override
    public MarshmistTitanCostReductionEffect copy() {
        return new MarshmistTitanCostReductionEffect(this);
    }
}
