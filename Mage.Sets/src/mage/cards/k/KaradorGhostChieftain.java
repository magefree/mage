package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.CastFromGraveyardOnceEachTurnAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class KaradorGhostChieftain extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature spell");

    public KaradorGhostChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new KaradorGhostChieftainCostReductionEffect()));

        // Once during each of your turns, you may cast a creature spell from your graveyard.
        this.addAbility(new CastFromGraveyardOnceEachTurnAbility(filter));
    }

    private KaradorGhostChieftain(final KaradorGhostChieftain card) {
        super(card);
    }

    @Override
    public KaradorGhostChieftain copy() {
        return new KaradorGhostChieftain(this);
    }
}

class KaradorGhostChieftainCostReductionEffect extends CostModificationEffectImpl {

    KaradorGhostChieftainCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {1} less to cast for each creature card in your graveyard";
    }

    private KaradorGhostChieftainCostReductionEffect(final KaradorGhostChieftainCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public KaradorGhostChieftainCostReductionEffect copy() {
        return new KaradorGhostChieftainCostReductionEffect(this);
    }
}
