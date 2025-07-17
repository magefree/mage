package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class WarpAbility extends SpellAbility {

    public static final String WARP_ACTIVATION_VALUE_KEY = "warpActivation";
    private final boolean allowGraveyard;

    public WarpAbility(Card card, String manaString) {
        this(card, manaString, false);
    }

    public WarpAbility(Card card, String manaString, boolean allowGraveyard) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with Warp");
        this.zone = Zone.HAND;
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = TimingRule.SORCERY;
        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaString));
        this.setAdditionalCostsRuleVisible(false);
        this.allowGraveyard = allowGraveyard;
    }

    private WarpAbility(final WarpAbility ability) {
        super(ability);
        this.allowGraveyard = ability.allowGraveyard;
    }

    // The ability sets up a delayed trigger which can't be set up using the cost tag system
    public static void addDelayedTrigger(SpellAbility spellAbility, Game game) {
        if (spellAbility instanceof WarpAbility) {
            game.addDelayedTriggeredAbility(
                    new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new WarpExileEffect()), spellAbility
            );
        }
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        switch (game.getState().getZone(getSourceId())) {
            case GRAVEYARD:
                if (!allowGraveyard) {
                    break;
                }
            case HAND:
                return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        if (!super.activate(game, allowedIdentifiers, noMana)) {
            return false;
        }
        this.setCostsTag(WARP_ACTIVATION_VALUE_KEY, null);
        return true;
    }

    @Override
    public WarpAbility copy() {
        return new WarpAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Warp");
        if (getCosts().isEmpty()) {
            sb.append(' ');
        } else {
            sb.append("&mdash;");
        }
        sb.append(getManaCosts().getText());
        if (!getCosts().isEmpty()) {
            sb.append(", ");
            sb.append(getCosts().getText());
            sb.append('.');
        }
        sb.append(" <i>(You may cast this card from your hand for its warp cost. ");
        sb.append("Exile this creature at the beginning of the next end step, ");
        sb.append("then you may cast it from exile on a later turn.)</i>");
        return sb.toString();
    }

    public static String makeWarpString(UUID playerId) {
        return playerId + "- Warped";
    }
}

class WarpExileEffect extends OneShotEffect {

    private static class WarpCondition implements Condition {

        private final int turnNumber;

        WarpCondition(Game game) {
            this.turnNumber = game.getTurnNum();
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return game.getTurnNum() > turnNumber;
        }
    }

    WarpExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile this creature if it was cast for its warp cost";
    }

    private WarpExileEffect(final WarpExileEffect effect) {
        super(effect);
    }

    @Override
    public WarpExileEffect copy() {
        return new WarpExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || permanent.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter() + 1) {
            return false;
        }
        player.moveCardsToExile(
                permanent, source, game, true,
                CardUtil.getExileZoneId(WarpAbility.makeWarpString(player.getId()), game),
                "Warped by " + player.getLogName()
        );
        CardUtil.makeCardPlayable(
                game, source, permanent.getMainCard(), true,
                Duration.Custom, false, player.getId(), new WarpCondition(game)
        );
        return true;
    }
}
