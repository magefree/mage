
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public final class VrynWingmare extends CardImpl {

    public VrynWingmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Noncreature spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VrynWingmareCostReductionEffect()));
    }

    public VrynWingmare(final VrynWingmare card) {
        super(card);
    }

    @Override
    public VrynWingmare copy() {
        return new VrynWingmare(this);
    }
}

class VrynWingmareCostReductionEffect extends CostModificationEffectImpl {

    VrynWingmareCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Noncreature spells cost {1} more to cast";
    }

    VrynWingmareCostReductionEffect(VrynWingmareCostReductionEffect effect) {
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
    public VrynWingmareCostReductionEffect copy() {
        return new VrynWingmareCostReductionEffect(this);
    }

}
