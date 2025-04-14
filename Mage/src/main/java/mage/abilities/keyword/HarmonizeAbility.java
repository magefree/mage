package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SpellAbilityCastMode;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class HarmonizeAbility extends CastFromGraveyardAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public HarmonizeAbility(Card card, String manaString) {
        super(card, new ManaCostsImpl<>(manaString), SpellAbilityCastMode.HARMONIZE);
        this.setCostAdjuster(HarmonizeAbilityAdjuster.instance);
    }

    private HarmonizeAbility(final HarmonizeAbility ability) {
        super(ability);
    }

    @Override
    public HarmonizeAbility copy() {
        return new HarmonizeAbility(this);
    }

    @Override
    public String getRule() {
        return name + " <i>(You may cast this card from your graveyard for its harmonize cost. " +
                "You may tap a creature you control to reduce that cost by {X}, " +
                "where X is its power. Then exile this spell.)</i>";
    }
}

enum HarmonizeAbilityAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        if (game.inCheckPlayableState()) {
            int amount = game
                    .getBattlefield()
                    .getActivePermanents(
                            StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE,
                            ability.getControllerId(), ability, game
                    )
                    .stream()
                    .map(MageObject::getPower)
                    .mapToInt(MageInt::getValue)
                    .max()
                    .orElse(0);
            CardUtil.reduceCost(ability, amount);
            return;
        }
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, ability, game, 1
        ) || !player.chooseUse(
                Outcome.Tap, "Tap a creature to reduce the cost of this spell?", ability, game
        )) {
            return;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE);
        target.withNotTarget(true);
        target.withChooseHint("to pay for harmonize");
        player.choose(Outcome.Tap, target, ability, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return;
        }
        CardUtil.reduceCost(ability, permanent.getPower().getValue());
        FilterControlledPermanent filter = new FilterControlledPermanent("creature chosen to tap for harmonize");
        filter.add(new PermanentIdPredicate(permanent.getId()));
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
    }
}
