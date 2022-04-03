
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DashedCondition;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.DashAbility;
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
 * @author LevelX2
 */
public final class Warbringer extends CardImpl {

    public Warbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dash costs you pay cost {2} less (as long as this creature is on the battlefield).
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WarbringerSpellsCostReductionEffect()));

        // Dash {2}{R}
        this.addAbility(new DashAbility(this, "{2}{R}"));
    }

    private Warbringer(final Warbringer card) {
        super(card);
    }

    @Override
    public Warbringer copy() {
        return new Warbringer(this);
    }
}

class WarbringerSpellsCostReductionEffect extends CostModificationEffectImpl {

    public WarbringerSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Dash costs you pay cost {2} less (as long as this creature is on the battlefield)";
    }

    protected WarbringerSpellsCostReductionEffect(final WarbringerSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())) {
            if (game != null && game.inCheckPlayableState()) {
                Card card = game.getCard(source.getSourceId());
                if (card != null) {
                    return card.getAbilities(game).stream().anyMatch(a -> a instanceof DashAbility);
                }
            } else {
                return DashedCondition.instance.apply(game, abilityToModify);
            }
        }
        return false;
    }

    @Override
    public WarbringerSpellsCostReductionEffect copy() {
        return new WarbringerSpellsCostReductionEffect(this);
    }
}
