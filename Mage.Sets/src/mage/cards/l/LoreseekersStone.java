package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LoreseekersStone extends CardImpl {

    public LoreseekersStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {3}, {T}: Draw three cards. This ability costs {1} more to activate for each card in your hand.
        this.addAbility(new LoreseekersStoneActivatedAbility());
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new LoreseekersStoneCostIncreasingEffect()));
    }

    private LoreseekersStone(final LoreseekersStone card) {
        super(card);
    }

    @Override
    public LoreseekersStone copy() {
        return new LoreseekersStone(this);
    }
}

class LoreseekersStoneActivatedAbility extends SimpleActivatedAbility {

    public LoreseekersStoneActivatedAbility() {
        super(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(3)
                        .setText("Draw three cards. This ability costs {1} more to activate for each card in your hand"),
                new GenericManaCost(3));
        this.addCost(new TapSourceCost());
    }

    private LoreseekersStoneActivatedAbility(final LoreseekersStoneActivatedAbility ability) {
        super(ability);
    }

    @Override
    public LoreseekersStoneActivatedAbility copy() {
        return new LoreseekersStoneActivatedAbility(this);
    }
}

class LoreseekersStoneCostIncreasingEffect extends CostModificationEffectImpl {

    LoreseekersStoneCostIncreasingEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.INCREASE_COST);
    }

    private LoreseekersStoneCostIncreasingEffect(final LoreseekersStoneCostIncreasingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardUtil.increaseCost(abilityToModify, controller.getHand().size());
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return (abilityToModify instanceof LoreseekersStoneActivatedAbility) && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public LoreseekersStoneCostIncreasingEffect copy() {
        return new LoreseekersStoneCostIncreasingEffect(this);
    }
}
