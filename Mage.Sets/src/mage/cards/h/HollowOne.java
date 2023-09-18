
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.CardsCycledOrDiscardedThisTurnWatcher;

/**
 *
 * @author spjspj
 */
public final class HollowOne extends CardImpl {

    public HollowOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hollow One costs {2} less to cast for each card you've cycled or discarded this turn.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new HollowOneReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability, new CardsCycledOrDiscardedThisTurnWatcher());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private HollowOne(final HollowOne card) {
        super(card);
    }

    @Override
    public HollowOne copy() {
        return new HollowOne(this);
    }
}

class HollowOneReductionEffect extends CostModificationEffectImpl {

    public HollowOneReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {2} less to cast for each card you've cycled or discarded this turn";
    }

    protected HollowOneReductionEffect(HollowOneReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardsCycledOrDiscardedThisTurnWatcher watcher = game.getState().getWatcher(CardsCycledOrDiscardedThisTurnWatcher.class);
        if (watcher != null) {
            CardUtil.reduceCost(abilityToModify, watcher.getNumberOfCardsCycledOrDiscardedThisTurn(source.getControllerId()) * 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public HollowOneReductionEffect copy() {
        return new HollowOneReductionEffect(this);
    }
}
