package mage.abilities.keyword;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AlternativeCost2;
import mage.abilities.costs.AlternativeCost2Impl;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public class ForetellAbility extends SpecialAction {

    private String foretellCost;
    private Card card;

    public ForetellAbility(Card card, String foretellCost) {
        super(Zone.HAND);
        this.foretellCost = foretellCost;
        this.card = card;
        this.usesStack = Boolean.FALSE;
        this.addCost(new GenericManaCost(2));
        this.addEffect(new ForetellExileEffect(card)); // exile the card
        this.addEffect(new ForetellPlayFromExileEffect(card));  // cast from exile with foretell
        addSubAbility(new ForetellCostAbility(card, foretellCost));  // foretell alternative cost
        addSubAbility(new SimpleStaticAbility(Zone.ALL, new ForetellLookAtCardEffect()));  // look at face-down card anytime
        this.setRuleVisible(false);
    }

    private ForetellAbility(ForetellAbility ability) {
        super(ability);
        this.foretellCost = ability.foretellCost;
        this.card = ability.card;
    }

    @Override
    public ForetellAbility copy() {
        return new ForetellAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // activate only during the controller's turn
        if (!game.isActivePlayer(this.getControllerId())) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }
}

class ForetellLookAtCardEffect extends AsThoughEffectImpl {

    public ForetellLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.AIDontUseIt);
    }

    public ForetellLookAtCardEffect(final ForetellLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ForetellLookAtCardEffect copy() {
        return new ForetellLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null
                    && CardUtil.getExileZoneId(card.getId().toString() + "foretellAbility", game) != null) {
                MageObject sourceObject = game.getObject(source.getSourceId());
                if (sourceObject == null) {
                    return false;
                }
                UUID exileId = CardUtil.getExileZoneId(card.getId().toString() + "foretellAbility", game);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null
                        && exile.contains(card.getId());
            }
        }
        return false;
    }
}

class ForetellExileEffect extends OneShotEffect {

    private Card card;

    public ForetellExileEffect(Card card) {
        super(Outcome.Neutral);
        this.card = card;
    }

    public ForetellExileEffect(final ForetellExileEffect effect) {
        super(effect);
        this.card = effect.card;
    }

    @Override
    public ForetellExileEffect copy() {
        return new ForetellExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && card != null) {
            UUID exileId = CardUtil.getExileZoneId(card.getId().toString() + "foretellAbility", game);
            Effect effect = new ExileTargetEffect(exileId, " Foretell Turn Number: " + game.getTurnNum());  // foretell turn number shows up on exile window
            game.getState().setValue(card.getId().toString() + "Foretell Turn Number", game.getTurnNum());  // remember turn number it was cast
            effect.setTargetPointer(new FixedTarget(card.getId()));
            effect.apply(game, source);
            card.setFaceDown(true, game);
            return true;
        }
        return false;
    }
}

class ForetellPlayFromExileEffect extends AsThoughEffectImpl {

    private Card card;

    ForetellPlayFromExileEffect(Card card) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE,
                Duration.EndOfGame, Outcome.Neutral);
        this.card = card;
        staticText = "Foretell";
    }

    private ForetellPlayFromExileEffect(final ForetellPlayFromExileEffect effect) {
        super(effect);
        this.card = effect.card;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ForetellPlayFromExileEffect copy() {
        return new ForetellPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (!Objects.equals(source.getControllerId(), playerId)) {
            return false;
        }

        Player player = game.getPlayer(playerId);
        if (player != null
                && game.getState().getValue(card.getId().toString() + "Foretell Turn Number") != null) {
            UUID objectIdToCast = CardUtil.getMainCardId(game, objectId);
            if (card == null
                    || !card.getId().equals(objectIdToCast)
                    || Zone.EXILED != game.getState().getZone(card.getId())
                    || ((int) game.getState().getValue(card.getId().toString() + "Foretell Turn Number") == game.getTurnNum())) {
                return false;
            }
            return true;
        }
        return false;
    }
}

class ForetellCostAbility extends StaticAbility implements AlternativeSourceCosts {

    // Stolen from LevelX's code
    protected static final String FORETELL_KEYWORD = "Foretell";
    protected static final String REMINDER_TEXT = "(During your turn, you may pay {2} and exile this card from your hand face down. Cast it on a later turn for its foretell cost.)";

    protected List<AlternativeCost2> foretellCosts = new LinkedList<>();

    // needed to check activation status, if card changes zone after casting it
    private int zoneChangeCounter = 0;

    public ForetellCostAbility(Card card, String foretellCost) {
        super(Zone.EXILED, null);
        name = FORETELL_KEYWORD;
        this.addForetellCost(foretellCost);
    }

    public ForetellCostAbility(final ForetellCostAbility ability) {
        super(ability);
        this.foretellCosts.addAll(ability.foretellCosts);
        this.zoneChangeCounter = ability.zoneChangeCounter;
    }

    @Override
    public ForetellCostAbility copy() {
        return new ForetellCostAbility(this);
    }

    public final AlternativeCost2 addForetellCost(String manaString) {
        AlternativeCost2 foretellCost = new AlternativeCost2Impl(FORETELL_KEYWORD, REMINDER_TEXT, new ManaCostsImpl(manaString));
        foretellCosts.add(foretellCost);
        return foretellCost;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null
                && card.getZoneChangeCounter(game) <= zoneChangeCounter + 1) {
            for (AlternativeCost2 cost : foretellCosts) {
                if (cost.isActivated(game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            return Zone.STACK == game.getState().getZone(card.getId())
                    && ((int) game.getState().getValue(card.getId().toString()
                            + "Foretell Turn Number") != game.getTurnNum());
        }
        return false;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetForetell();
                for (AlternativeCost2 foretellCost : foretellCosts) {
                    if (foretellCost.canPay(ability, this, controllerId, game)
                            && player.chooseUse(Outcome.Benefit,
                                    new StringBuilder(FORETELL_KEYWORD).append(" ")
                                            .append(foretellCost.getText(true)).append(" ?").toString(), ability, game)) {
                        activateForetellCost(foretellCost, game);
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = ((Costs) foretellCost).iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCostsImpl) {
                                ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                    }
                }
            }
        }
        return isActivated(ability, game);
    }

    private void activateForetellCost(AlternativeCost2 cost, Game game) {
        cost.activate();
        // remember zone change counter
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
            } else {
                throw new IllegalArgumentException("Foretell source card not found");
            }
        }
    }

    @Override
    public Costs<Cost> getCosts() {
        Costs<Cost> alterCosts = new CostsImpl<>();
        for (AlternativeCost2 aCost : foretellCosts) {
            alterCosts.add(aCost.getCost());
        }
        return alterCosts;
    }

    public void resetForetell() {
        for (AlternativeCost2 cost : foretellCosts) {
            cost.reset();
        }
        zoneChangeCounter = 0;
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (AlternativeCost2 cost : foretellCosts) {
            if (cost.isActivated(game)) {
                sb.append(cost.getCastSuffixMessage(position));
                ++position;
            }
        }
        return sb.toString();
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        int numberCosts = 0;
        String remarkText = "";
        for (AlternativeCost2 foretellCost : foretellCosts) {
            if (numberCosts == 0) {
                sb.append(foretellCost.getText(false));
                remarkText = foretellCost.getReminderText();
            } else {
                sb.append(" and/or ").append(foretellCost.getText(true));
            }
            ++numberCosts;
        }
        if (numberCosts == 1) {
            sb.append(' ').append(remarkText);
        }

        return sb.toString();
    }
}
