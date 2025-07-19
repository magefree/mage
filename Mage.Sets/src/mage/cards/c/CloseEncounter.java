package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloseEncounter extends CardImpl {

    public CloseEncounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // As an additional cost to cast this spell, choose a creature you control or a warped creature card you own in exile.
        this.getSpellAbility().addCost(new CloseEncounterCost());

        // Close Encounter deals damage equal to the power of the chosen creature or card to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(CloseEncounterValue.instance)
                .setText("{this} deals damage equal to the power of the chosen creature or card to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CloseEncounter(final CloseEncounter card) {
        super(card);
    }

    @Override
    public CloseEncounter copy() {
        return new CloseEncounter(this);
    }
}

enum CloseEncounterValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Card) effect.getValue("closeEncounterCost"))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public CloseEncounterValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the power of the chosen creature or card";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class CloseEncounterCost extends CostImpl {

    private static final FilterCard filterCard = new FilterCreatureCard("warped creature card you own in exile");

    public CloseEncounterCost() {
        super();
        this.text = "choose a creature you control or a warped creature card you own in exile";
    }

    private CloseEncounterCost(final CloseEncounterCost cost) {
        super(cost);
    }

    @Override
    public CloseEncounterCost copy() {
        return new CloseEncounterCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return Optional
                .ofNullable(CardUtil.getExileZoneId(WarpAbility.makeWarpString(controllerId), game))
                .map(game.getExile()::getExileZone)
                .filter(exileZone -> !exileZone.getCards(filterCard, game).isEmpty())
                .isPresent()
                || game
                .getBattlefield()
                .contains(StaticFilters.FILTER_CONTROLLED_CREATURE, controllerId, source, game, 1);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            paid = false;
            return paid;
        }
        boolean hasPermanent = game
                .getBattlefield()
                .contains(StaticFilters.FILTER_CONTROLLED_CREATURE, controllerId, source, game, 1);
        boolean hasWarp = Optional
                .ofNullable(CardUtil.getExileZoneId(WarpAbility.makeWarpString(controllerId), game))
                .map(game.getExile()::getExileZone)
                .filter(exileZone -> !exileZone.getCards(filterCard, game).isEmpty())
                .isPresent();
        boolean usePermanent;
        if (hasPermanent && hasWarp) {
            usePermanent = player.chooseUse(
                    Outcome.Neutral, "Choose a creature you control or a warped creature you own in exile?",
                    null, "Choose controlled", "Choose from exile", source, game
            );
        } else if (hasPermanent) {
            usePermanent = true;
        } else if (hasWarp) {
            usePermanent = false;
        } else {
            paid = false;
            return paid;
        }
        if (usePermanent) {
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.withNotTarget(true);
            player.choose(Outcome.Neutral, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                paid = false;
                return paid;
            }
            game.informPlayers(player.getLogName() + " chooses " + permanent.getLogName() + " on the battlefield");
            source.getEffects().setValue("closeEncounterCost", permanent);
            paid = true;
            return true;
        }
        TargetCard target = new TargetCardInExile(
                filterCard, CardUtil.getExileZoneId(WarpAbility.makeWarpString(controllerId), game)
        );
        player.choose(Outcome.Neutral, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            paid = false;
            return paid;
        }
        game.informPlayers(player.getLogName() + " chooses " + card.getLogName() + " from exile");
        source.getEffects().setValue("closeEncounterCost", card);
        paid = true;
        return paid;
    }
}
