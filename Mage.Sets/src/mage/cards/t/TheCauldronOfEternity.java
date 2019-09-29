package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCauldronOfEternity extends CardImpl {

    public TheCauldronOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{10}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);

        // This spell costs {2} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TheCauldronOfEternityCostReductionEffect()));

        // Whenever a creature you control dies, put it on the bottom of its owner's library.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new PutOnLibraryTargetEffect(false, "put it on the bottom of its owner's library"),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE, true
        ));

        // {2}{B}, {T}, Pay 2 life: Return target creature card from your graveyard to the battlefield. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl("{2}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(2));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private TheCauldronOfEternity(final TheCauldronOfEternity card) {
        super(card);
    }

    @Override
    public TheCauldronOfEternity copy() {
        return new TheCauldronOfEternity(this);
    }
}

class TheCauldronOfEternityCostReductionEffect extends CostModificationEffectImpl {

    TheCauldronOfEternityCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {2} less to cast for each creature card in your graveyard";
    }

    private TheCauldronOfEternityCostReductionEffect(final TheCauldronOfEternityCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int reductionAmount = player
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(MageObject::isCreature)
                .mapToInt(card -> 2)
                .sum();
        CardUtil.reduceCost(abilityToModify, reductionAmount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public TheCauldronOfEternityCostReductionEffect copy() {
        return new TheCauldronOfEternityCostReductionEffect(this);
    }
}
