
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
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
 *
 * @author LevelX2
 */
public final class LocketOfYesterdays extends CardImpl {

    public LocketOfYesterdays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Spells you cast cost {1} less to cast for each card with the same name as that spell in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LocketOfYesterdaysCostReductionEffect()));
    }

    private LocketOfYesterdays(final LocketOfYesterdays card) {
        super(card);
    }

    @Override
    public LocketOfYesterdays copy() {
        return new LocketOfYesterdays(this);
    }
}

class LocketOfYesterdaysCostReductionEffect extends CostModificationEffectImpl {

    LocketOfYesterdaysCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Spells you cast cost {1} less to cast for each card with the same name as that spell in your graveyard";
    }

    private LocketOfYesterdaysCostReductionEffect(final LocketOfYesterdaysCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        MageObject sourceObject = game.getObject(abilityToModify.getSourceId());
        if (sourceObject != null) {
            int amount = 0;
            for (UUID cardId : game.getPlayer(source.getControllerId()).getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.getName().equals(sourceObject.getName())) {
                    amount++;
                }
            }
            if (amount > 0) {
                SpellAbility spellAbility = (SpellAbility) abilityToModify;
                CardUtil.adjustCost(spellAbility, amount);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.isControlledBy(source.getControllerId())
                && (abilityToModify instanceof SpellAbility)) {
            return true;
        }
        return false;
    }

    @Override
    public LocketOfYesterdaysCostReductionEffect copy() {
        return new LocketOfYesterdaysCostReductionEffect(this);
    }

}
