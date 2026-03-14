package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DargoTheShipwrecker extends CardImpl {

    public DargoTheShipwrecker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, you may sacrifice any number of artifacts and/or creatures. This spell costs {2} less to cast for each permanent sacrificed this way and {2} less to cast for each other artifact or creature you've sacrificed this turn.
        Cost cost = new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE, true);
        cost.setText("you may sacrifice any number of artifacts and/or creatures. " +
                "This spell costs {2} less to cast for each permanent sacrificed this way " +
                "and {2} less to cast for each other artifact or creature you've sacrificed this turn");
        this.getSpellAbility().addCost(cost);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new DargoTheShipwreckerEffect());
        ability.setRuleVisible(false);
        this.addAbility(ability, new PermanentsSacrificedWatcher());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private DargoTheShipwrecker(final DargoTheShipwrecker card) {
        super(card);
    }

    @Override
    public DargoTheShipwrecker copy() {
        return new DargoTheShipwrecker(this);
    }
}

class DargoTheShipwreckerEffect extends CostModificationEffectImpl {

    DargoTheShipwreckerEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private DargoTheShipwreckerEffect(final DargoTheShipwreckerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int reduction = 0;
        for (Cost cost : spellAbility.getCosts()) {
            if (!(cost instanceof SacrificeXTargetCost)) {
                continue;
            }
            if (game.inCheckPlayableState()) {
                // allows to cast in getPlayable
                reduction += ((SacrificeXTargetCost) cost).getMaxValue(spellAbility, game);
            } else {
                // real cast
                reduction += ((SacrificeXTargetCost) cost).getAmount();
            }
            break;
        }
        reduction += (int) game
                .getState()
                .getWatcher(PermanentsSacrificedWatcher.class)
                .getThisTurnSacrificedPermanents(source.getControllerId())
                .stream()
                .filter(permanent -> permanent.isArtifact(game) || permanent.isCreature(game))
                .count();

        CardUtil.adjustCost(spellAbility, reduction * 2);

        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public DargoTheShipwreckerEffect copy() {
        return new DargoTheShipwreckerEffect(this);
    }
}
