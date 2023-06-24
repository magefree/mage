package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TorgaarFamineIncarnate extends CardImpl {

    public TorgaarFamineIncarnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // As an additional cost to cast this spell, you may sacrifice any number of creatures.
        Cost cost = new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT);
        cost.setText("As an additional cost to cast this spell, you may sacrifice any number of creatures");
        this.getSpellAbility().addCost(cost);
        // This spell costs {2} less to cast for each creature sacrificed this way.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TorgaarFamineIncarnateEffectCostReductionEffect()));

        // When Torgaar, Famine Incarnate enters the battlefield, up to one target player's life total becomes half their starting life total, rounded down.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TorgaarFamineIncarnateEffect(), false);
        ability.addTarget(new TargetPlayer(0, 1, false));
        this.addAbility(ability);

    }

    private TorgaarFamineIncarnate(final TorgaarFamineIncarnate card) {
        super(card);
    }

    @Override
    public TorgaarFamineIncarnate copy() {
        return new TorgaarFamineIncarnate(this);
    }
}

class TorgaarFamineIncarnateEffect extends OneShotEffect {

    public TorgaarFamineIncarnateEffect() {
        super(Outcome.Benefit);
        this.staticText = "up to one target player's life total becomes half their starting life total, rounded down";
    }

    public TorgaarFamineIncarnateEffect(final TorgaarFamineIncarnateEffect effect) {
        super(effect);
    }

    @Override
    public TorgaarFamineIncarnateEffect copy() {
        return new TorgaarFamineIncarnateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            int startingLifeTotal = game.getStartingLife();
            targetPlayer.setLife(startingLifeTotal / 2, game, source);
        }
        return true;
    }
}

class TorgaarFamineIncarnateEffectCostReductionEffect extends CostModificationEffectImpl {

    public TorgaarFamineIncarnateEffectCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {2} less to cast for each creature sacrificed this way";
    }

    public TorgaarFamineIncarnateEffectCostReductionEffect(final TorgaarFamineIncarnateEffectCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        for (Cost cost : spellAbility.getCosts()) {
            if (cost instanceof SacrificeXTargetCost) {
                if (game.inCheckPlayableState()) {
                    // allows to cast in getPlayable
                    int reduction = ((SacrificeXTargetCost) cost).getMaxValue(spellAbility, game);
                    CardUtil.adjustCost(spellAbility, reduction * 2);
                } else {
                    // real cast
                    int reduction = ((SacrificeXTargetCost) cost).getAmount();
                    CardUtil.adjustCost(spellAbility, reduction * 2);
                }

                break;
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public TorgaarFamineIncarnateEffectCostReductionEffect copy() {
        return new TorgaarFamineIncarnateEffectCostReductionEffect(this);
    }
}
