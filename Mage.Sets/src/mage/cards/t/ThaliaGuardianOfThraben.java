
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward
 */
public final class ThaliaGuardianOfThraben extends CardImpl {

    public ThaliaGuardianOfThraben(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());

        // Noncreature spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThaliaGuardianOfThrabenCostReductionEffect()));

    }

    public ThaliaGuardianOfThraben(final ThaliaGuardianOfThraben card) {
        super(card);
    }

    @Override
    public ThaliaGuardianOfThraben copy() {
        return new ThaliaGuardianOfThraben(this);
    }
}

class ThaliaGuardianOfThrabenCostReductionEffect extends CostModificationEffectImpl {

    ThaliaGuardianOfThrabenCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Noncreature spells cost {1} more to cast";
    }

    ThaliaGuardianOfThrabenCostReductionEffect(ThaliaGuardianOfThrabenCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card card = game.getCard(abilityToModify.getSourceId());
            if (card != null && !card.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ThaliaGuardianOfThrabenCostReductionEffect copy() {
        return new ThaliaGuardianOfThrabenCostReductionEffect(this);
    }

}
