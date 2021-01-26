package mage.abilities.keyword;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.SplitCard;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
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
        // exile the card
        this.addEffect(new ForetellExileEffect(card));
        // foretell cost from exile : it can't be any other cost
        addSubAbility(new ForetellCostAbility(foretellCost));
        // look at face-down card anytime
        addSubAbility(new SimpleStaticAbility(Zone.ALL, new ForetellLookAtCardEffect()));
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
        staticText = "Foretold this card";
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
            // foretell turn number shows up on exile window
            Effect effect = new ExileTargetEffect(exileId, " Foretell Turn Number: " + game.getTurnNum());
            // remember turn number it was cast
            game.getState().setValue(card.getId().toString() + "Foretell Turn Number", game.getTurnNum());
            effect.setTargetPointer(new FixedTarget(card.getId()));
            effect.apply(game, source);
            card.setFaceDown(true, game);
            return true;
        }
        return false;
    }
}

class ForetellCostAbility extends SpellAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public ForetellCostAbility(String foretellCost) {
        super(null, "Foretell", Zone.EXILED, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.NORMAL);
        this.setAdditionalCostsRuleVisible(false);
        this.name = "Foretell " + foretellCost;
        this.addCost(new ManaCostsImpl(foretellCost));
    }

    public ForetellCostAbility(final ForetellCostAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.abilityName = ability.abilityName;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                // Card must be in the exile zone
                if (game.getState().getZone(card.getId()) != Zone.EXILED) {
                    return ActivationStatus.getFalse();
                }
                // Card must be Foretold
                if (game.getState().getValue(card.getId().toString() + "Foretell Turn Number") == null
                        && game.getState().getValue(card.getId().toString() + "foretellAbility") == null) {
                    return ActivationStatus.getFalse();
                }
                // Can't be cast if the turn it was Foretold is the same
                if ((int) game.getState().getValue(card.getId().toString() + "Foretell Turn Number") == game.getTurnNum()) {
                    return ActivationStatus.getFalse();
                }
                // Check that the card is actually in the exile zone (ex: Oblivion Ring exiles it after it was Foretold, etc)
                UUID exileId = (UUID) game.getState().getValue(card.getId().toString() + "foretellAbility");
                ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                if (exileZone != null
                        && exileZone.isEmpty()) {
                    return ActivationStatus.getFalse();
                }
                // Cards with no Mana Costs cant't be flashbacked (e.g. Ancestral Vision)
                if (card.getManaCost().isEmpty()) {
                    return ActivationStatus.getFalse();
                }
                if (card instanceof SplitCard) {
                    if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
                    }
                } else if (card instanceof ModalDoubleFacesCard) {
                    if (((ModalDoubleFacesCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        return ((ModalDoubleFacesCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((ModalDoubleFacesCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        return ((ModalDoubleFacesCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
                    }
                }
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public SpellAbility getSpellAbilityToResolve(Game game) {
        Card card = game.getCard(getSourceId());
        if (card != null) {
            if (spellAbilityToResolve == null) {
                SpellAbility spellAbilityCopy = null;
                if (card instanceof SplitCard) {
                    if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((SplitCard) card).getLeftHalfCard().getSpellAbility().copy();
                    } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((SplitCard) card).getRightHalfCard().getSpellAbility().copy();
                    }
                } else if (card instanceof ModalDoubleFacesCard) {
                    if (((ModalDoubleFacesCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((ModalDoubleFacesCard) card).getLeftHalfCard().getSpellAbility().copy();
                    } else if (((ModalDoubleFacesCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((ModalDoubleFacesCard) card).getRightHalfCard().getSpellAbility().copy();
                    }
                } else {
                    spellAbilityCopy = card.getSpellAbility().copy();
                }
                if (spellAbilityCopy == null) {
                    return null;
                }
                spellAbilityCopy.setId(this.getId());
                spellAbilityCopy.getManaCosts().clear();
                spellAbilityCopy.getManaCostsToPay().clear();
                spellAbilityCopy.getCosts().addAll(this.getCosts().copy());
                spellAbilityCopy.addCost(this.getManaCosts().copy());
                spellAbilityCopy.setSpellAbilityCastMode(this.getSpellAbilityCastMode());
                spellAbilityToResolve = spellAbilityCopy;
            }
        }
        return spellAbilityToResolve;
    }

    @Override
    public Costs<Cost> getCosts() {
        if (spellAbilityToResolve == null) {
            return super.getCosts();
        }
        return spellAbilityToResolve.getCosts();
    }

    @Override
    public ForetellCostAbility copy() {
        return new ForetellCostAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Foretell");
        if (!costs.isEmpty()) {
            sbRule.append("&mdash;");
        } else {
            sbRule.append(' ');
        }
        if (!manaCosts.isEmpty()) {
            sbRule.append(manaCosts.getText());
        }
        if (!costs.isEmpty()) {
            if (!manaCosts.isEmpty()) {
                sbRule.append(", ");
            }
            sbRule.append(costs.getText());
            sbRule.append('.');
        }
        if (abilityName != null) {
            sbRule.append(' ');
            sbRule.append(abilityName);
        }
        sbRule.append(" <i>(During your turn, you may pay {2} and exile this card from your hand face down. Cast it on a later turn for its foretell cost.)</i>");
        return sbRule.toString();
    }

    /**
     * Used for split card in PlayerImpl method:
     * getOtherUseableActivatedAbilities
     *
     * @param abilityName
     */
    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

}
