package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LocketOfYesterdays extends CardImpl {

    public LocketOfYesterdays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Spells you cast cost {1} less to cast for each card with the same name as that spell in your graveyard.
        this.addAbility(new SimpleStaticAbility(new LocketOfYesterdaysCostReductionEffect()));
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
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(abilityToModify.getSourceId());
        if (player == null || sourceObject == null) {
            return false;
        }
        int amount = player
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(card -> card.sharesName(sourceObject, game))
                .mapToInt(x -> 1)
                .sum();
        if (amount > 0) {
            CardUtil.adjustCost((SpellAbility) abilityToModify, amount);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && abilityToModify instanceof SpellAbility;
    }

    @Override
    public LocketOfYesterdaysCostReductionEffect copy() {
        return new LocketOfYesterdaysCostReductionEffect(this);
    }
}
