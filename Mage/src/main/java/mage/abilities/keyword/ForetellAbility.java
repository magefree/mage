package mage.abilities.keyword;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.ForetoldWatcher;

import java.util.UUID;
import mage.game.events.GameEvent;

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

    public class ForetellExileEffect extends OneShotEffect {

        private final Card card;
        String foretellCost;
        String foretellSplitCost;

        public ForetellExileEffect(Card card, String foretellCost, String foretellSplitCost) {
            super(Outcome.Neutral);
            this.card = card;
            this.foretellCost = foretellCost;
            this.foretellSplitCost = foretellSplitCost;
        }

        public ForetellExileEffect(final ForetellExileEffect effect) {
            super(effect);
            this.card = effect.card;
            this.foretellCost = effect.foretellCost;
            this.foretellSplitCost = effect.foretellSplitCost;
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

                // get main card id
                UUID mainCardId = card.getMainCard().getId();

                // retrieve the exileId of the foretold card
                UUID exileId = CardUtil.getExileZoneId(mainCardId.toString() + "foretellAbility", game);

                // foretell turn number shows up on exile window
                ExileTargetEffect effect = new ExileTargetEffect(exileId, " Foretell Turn Number: " + game.getTurnNum());

                // remember turn number it was cast
                game.getState().setValue(mainCardId.toString() + "Foretell Turn Number", game.getTurnNum());

                // remember the foretell cost
                game.getState().setValue(mainCardId.toString() + "Foretell Cost", foretellCost);
                game.getState().setValue(mainCardId.toString() + "Foretell Split Cost", foretellSplitCost);

                // exile the card face-down
                effect.setWithName(false);
                effect.setTargetPointer(new FixedTarget(card.getId()));
                effect.apply(game, source);
                card.setFaceDown(true, game);
                game.addEffect(new ForetellAddCostEffect(new MageObjectReference(card, game)), source);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FORETELL, card.getId(), null, source.getControllerId()));
                return true;
            }
            return false;
        }
    }

    public class ForetellLookAtCardEffect extends AsThoughEffectImpl {

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
                if (card != null) {
                    MageObject sourceObject = game.getObject(source.getSourceId());
                    if (sourceObject == null) {
                        return false;
                    }
                    UUID mainCardId = card.getMainCard().getId();
                    UUID exileId = CardUtil.getExileZoneId(mainCardId.toString() + "foretellAbility", game);
                    ExileZone exile = game.getExile().getExileZone(exileId);
                    return exile != null
                            && exile.contains(mainCardId);
                }
            }
            return false;
        }
    }

    public class ForetellAddCostEffect extends ContinuousEffectImpl {

        private final MageObjectReference mor;

        public ForetellAddCostEffect(MageObjectReference mor) {
            super(Duration.EndOfGame, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            this.mor = mor;
            staticText = "Foretold card";
        }

        public ForetellAddCostEffect(final ForetellAddCostEffect effect) {
            super(effect);
            this.mor = effect.mor;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Card card = mor.getCard(game);
            if (card != null) {
                UUID mainCardId = card.getMainCard().getId();
                if (game.getState().getZone(mainCardId) == Zone.EXILED) {
                    String foretellCost = (String) game.getState().getValue(mainCardId.toString() + "Foretell Cost");
                    String foretellSplitCost = (String) game.getState().getValue(mainCardId.toString() + "Foretell Split Cost");
                    if (card instanceof SplitCard) {
                        if (foretellCost != null) {
                            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                            ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                            ability.setSourceId(leftHalfCard.getId());
                            ability.setControllerId(source.getControllerId());
                            ability.setSpellAbilityType(leftHalfCard.getSpellAbility().getSpellAbilityType());
                            ability.setAbilityName(leftHalfCard.getName());
                            game.getState().addOtherAbility(leftHalfCard, ability);
                        }
                        if (foretellSplitCost != null) {
                            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                            ForetellCostAbility ability = new ForetellCostAbility(foretellSplitCost);
                            ability.setSourceId(rightHalfCard.getId());
                            ability.setControllerId(source.getControllerId());
                            ability.setSpellAbilityType(rightHalfCard.getSpellAbility().getSpellAbilityType());
                            ability.setAbilityName(rightHalfCard.getName());
                            game.getState().addOtherAbility(rightHalfCard, ability);
                        }
                    } else if (card instanceof ModalDoubleFacesCard) {
                        if (foretellCost != null) {
                            ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                            // some MDFC's are land IE: sea gate restoration
                            if (!leftHalfCard.isLand(game)) {
                                ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                                ability.setSourceId(leftHalfCard.getId());
                                ability.setControllerId(source.getControllerId());
                                ability.setSpellAbilityType(leftHalfCard.getSpellAbility().getSpellAbilityType());
                                ability.setAbilityName(leftHalfCard.getName());
                                game.getState().addOtherAbility(leftHalfCard, ability);
                            }
                        }
                        if (foretellSplitCost != null) {
                            ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                            // some MDFC's are land IE: sea gate restoration
                            if (!rightHalfCard.isLand(game)) {
                                ForetellCostAbility ability = new ForetellCostAbility(foretellSplitCost);
                                ability.setSourceId(rightHalfCard.getId());
                                ability.setControllerId(source.getControllerId());
                                ability.setSpellAbilityType(rightHalfCard.getSpellAbility().getSpellAbilityType());
                                ability.setAbilityName(rightHalfCard.getName());
                                game.getState().addOtherAbility(rightHalfCard, ability);
                            }
                        }
                    } else if (card instanceof AdventureCard) {
                        if (foretellCost != null) {
                            Card creatureCard = card.getMainCard();
                            ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                            ability.setSourceId(creatureCard.getId());
                            ability.setControllerId(source.getControllerId());
                            ability.setSpellAbilityType(creatureCard.getSpellAbility().getSpellAbilityType());
                            ability.setAbilityName(creatureCard.getName());
                            game.getState().addOtherAbility(creatureCard, ability);
                        }
                        if (foretellSplitCost != null) {
                            Card spellCard = ((AdventureCard) card).getSpellCard();
                            ForetellCostAbility ability = new ForetellCostAbility(foretellSplitCost);
                            ability.setSourceId(spellCard.getId());
                            ability.setControllerId(source.getControllerId());
                            ability.setSpellAbilityType(spellCard.getSpellAbility().getSpellAbilityType());
                            ability.setAbilityName(spellCard.getName());
                            game.getState().addOtherAbility(spellCard, ability);
                        }
                    } else if (foretellCost != null) {
                        ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                        ability.setSourceId(card.getId());
                        ability.setControllerId(source.getControllerId());
                        ability.setSpellAbilityType(card.getSpellAbility().getSpellAbilityType());
                        ability.setAbilityName(card.getName());
                        game.getState().addOtherAbility(card, ability);
                    }
                    return true;
                }
            }
            discard();
            return true;
        }

        @Override
        public ForetellAddCostEffect copy() {
            return new ForetellAddCostEffect(this);
        }
    }

    public class ForetellCostAbility extends SpellAbility {

        private String abilityName;
        private SpellAbility spellAbilityToResolve;

        public ForetellCostAbility(String foretellCost) {
            super(null, "Testing", Zone.EXILED, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.NORMAL);
            // Needed for Dream Devourer and Ethereal Valkyrie reducing the cost of a colorless CMC 2 or less spell to 0
            // CardUtil.reduceCost returns an empty string in that case so we add a cost of 0 here
            // https://github.com/magefree/mage/issues/7607
            if (foretellCost != null && foretellCost.isEmpty()) {
                foretellCost = "{0}";
            }
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
                    UUID mainCardId = card.getMainCard().getId();
                    // Card must be in the exile zone
                    if (game.getState().getZone(mainCardId) != Zone.EXILED) {
                        return ActivationStatus.getFalse();
                    }
                    // Card must be Foretold
                    if (game.getState().getValue(mainCardId.toString() + "Foretell Turn Number") == null
                            && game.getState().getValue(mainCardId + "foretellAbility") == null) {
                        return ActivationStatus.getFalse();
                    }
                    // Can't be cast if the turn it was Foretold is the same
                    if ((int) game.getState().getValue(mainCardId.toString() + "Foretell Turn Number") == game.getTurnNum()) {
                        return ActivationStatus.getFalse();
                    }
                    // Check that the card is actually in the exile zone (ex: Oblivion Ring exiles it after it was Foretold, etc)
                    UUID exileId = (UUID) game.getState().getValue(mainCardId.toString() + "foretellAbility");
                    ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                    if (exileZone != null
                            && exileZone.isEmpty()) {
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
                    } else if (card instanceof AdventureCard) {
                        if (card.getMainCard().getName().equals(abilityName)) {
                            return card.getMainCard().getSpellAbility().canActivate(playerId, game);
                        } else if (((AdventureCard) card).getSpellCard().getName().equals(abilityName)) {
                            return ((AdventureCard) card).getSpellCard().getSpellAbility().canActivate(playerId, game);
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
                    } else if (card instanceof AdventureCard) {
                        if (card.getMainCard().getName().equals(abilityName)) {
                            spellAbilityCopy = card.getMainCard().getSpellAbility().copy();
                        } else if (((AdventureCard) card).getSpellCard().getName().equals(abilityName)) {
                            spellAbilityCopy = ((AdventureCard) card).getSpellCard().getSpellAbility().copy();
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
            sbRule.append(" <i>(You may cast this card from exile for its foretell cost.)</i>");
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
}
