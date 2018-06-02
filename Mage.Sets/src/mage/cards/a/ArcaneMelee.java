
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public final class ArcaneMelee extends CardImpl {

    public ArcaneMelee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // Instant and sorcery spells cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArcaneMeleeCostReductionEffect()));
    }

    public ArcaneMelee(final ArcaneMelee card) {
        super(card);
    }

    @Override
    public ArcaneMelee copy() {
        return new ArcaneMelee(this);
    }
}

class ArcaneMeleeCostReductionEffect extends CostModificationEffectImpl {

    ArcaneMeleeCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Instant and sorcery spells cost {2} less to cast";
    }

    ArcaneMeleeCostReductionEffect(ArcaneMeleeCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card sourceCard = game.getCard((abilityToModify).getSourceId());
            if (sourceCard != null && (sourceCard.isInstant() || sourceCard.isSorcery())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArcaneMeleeCostReductionEffect copy() {
        return new ArcaneMeleeCostReductionEffect(this);
    }

}
