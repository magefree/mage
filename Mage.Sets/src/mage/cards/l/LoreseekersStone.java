
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class LoreseekersStone extends CardImpl {

    public LoreseekersStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {3}, {T}: Draw three cards. This ability costs {1} more to activate for each card in your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(3), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        for (Effect effect : ability.getEffects()) {
            effect.setText("Draw three cards. This ability costs {1} more to activate for each card in your hand");
        }
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new LoreseekersStoneCostIncreasingEffect(ability.getOriginalId())));

    }

    public LoreseekersStone(final LoreseekersStone card) {
        super(card);
    }

    @Override
    public LoreseekersStone copy() {
        return new LoreseekersStone(this);
    }
}

class LoreseekersStoneCostIncreasingEffect extends CostModificationEffectImpl {

    private final UUID originalId;

    LoreseekersStoneCostIncreasingEffect(UUID originalId) {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.originalId = originalId;
    }

    LoreseekersStoneCostIncreasingEffect(final LoreseekersStoneCostIncreasingEffect effect) {
        super(effect);
        this.originalId = effect.originalId;
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
        return abilityToModify.getOriginalId().equals(originalId);
    }

    @Override
    public LoreseekersStoneCostIncreasingEffect copy() {
        return new LoreseekersStoneCostIncreasingEffect(this);
    }

}
