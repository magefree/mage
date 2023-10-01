package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.ForetoldWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class ForetellAbility extends SpecialAction {

    private final String foretellCost;
    private final String foretellSplitCost;
    private final Card card;

    public ForetellAbility(Card card, String foretellCost) {
        this(card, foretellCost, null);
    }

    public ForetellAbility(Card card, String foretellCost, String foretellSplitCost) {
        super(Zone.HAND);
        this.foretellCost = foretellCost;
        this.foretellSplitCost = foretellSplitCost;
        this.card = card;
        this.usesStack = Boolean.FALSE;
        this.addCost(new GenericManaCost(2));
        // exile the card and it can't be cast the turn it was foretold
        this.addEffect(new ForetellExileEffect(card, foretellCost, foretellSplitCost));
        // look at face-down card anytime
        Ability ability = new SimpleStaticAbility(Zone.ALL, new ForetellLookAtCardEffect());
        ability.setControllerId(controllerId);  // if not set, anyone can look at the card in exile
        addSubAbility(ability);
        this.setRuleVisible(true);
        this.addWatcher(new ForetoldWatcher());
    }

    private ForetellAbility(ForetellAbility ability) {
        super(ability);
        this.foretellCost = ability.foretellCost;
        this.foretellSplitCost = ability.foretellSplitCost;
        this.card = ability.card;
    }

    @Override
    public ForetellAbility copy() {
        return new ForetellAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // activate only during the controller's turn
        if (game.getState().getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.ALLOW_FORETELL_ANYTIME, game).isEmpty()
                && !game.isActivePlayer(this.getControllerId())) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public String getRule() {
        return "Foretell " + foretellCost + " <i>(During your turn, "
                + "you may pay {2} and exile this card from your hand face down. "
                + "Cast it on a later turn for its foretell cost.)</i>";
    }

    @Override
    public String getGameLogMessage(Game game) {
        return " foretells a card from hand";
    }
}

