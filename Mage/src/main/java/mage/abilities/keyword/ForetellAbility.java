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
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.ForetoldWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class ForetellAbility extends SpecialAction {

    private final String foretellCost;
    private final Card card;

    public ForetellAbility(Card card, String foretellCost) {
        super(Zone.HAND);
        this.foretellCost = foretellCost;
        this.card = card;
        this.usesStack = Boolean.FALSE;
        this.addCost(new GenericManaCost(2));
        // exile the card and it can't be cast the turn it was foretold
        this.addEffect(new ForetellExileEffect(card, foretellCost));
        // look at face-down card anytime
        addSubAbility(new SimpleStaticAbility(Zone.ALL, new ForetellLookAtCardEffect()));
        this.setRuleVisible(true);
        this.addWatcher(new ForetoldWatcher());
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
        if (game.getState().getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.ALLOW_FORETELL_ANYTIME, game).isEmpty()
                && !game.isActivePlayer(this.getControllerId())) {
            // TODO: must be fixed to call super.canActivate here for additional checks someday
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

    public class ForetellExileEffect extends OneShotEffect {

        private final Card card;
        String foretellCost;

        public ForetellExileEffect(Card card, String foretellCost) {
            super(Outcome.Neutral);
            this.card = card;
            this.foretellCost = foretellCost;
        }

        public ForetellExileEffect(final ForetellExileEffect effect) {
            super(effect);
            this.card = effect.card;
            this.foretellCost = effect.foretellCost;
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
                // retrieve the exileId of the foretold card
                UUID exileId = CardUtil.getExileZoneId(card.getId().toString() + "foretellAbility", game);

                // foretell turn number shows up on exile window
                Effect effect = new ExileTargetEffect(exileId, " Foretell Turn Number: " + game.getTurnNum());

                // remember turn number it was cast
                game.getState().setValue(card.getId().toString() + "Foretell Turn Number", game.getTurnNum());

                // remember the foretell cost
                game.getState().setValue(card.getId().toString() + "Foretell Cost", foretellCost);

                // exile the card face-down
                effect.setTargetPointer(new FixedTarget(card.getId()));
                effect.apply(game, source);
                card.setFaceDown(true, game);
                game.addEffect(new ForetellAddCostEffect(new MageObjectReference(card, game)), source);
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
            if (card != null
                    && game.getState().getZone(card.getId()) == Zone.EXILED) {
                String foretellCost = (String) game.getState().getValue(card.getId().toString() + "Foretell Cost");
                Ability ability = new ForetellCostAbility(foretellCost);
                ability.setSourceId(card.getId());
                ability.setControllerId(source.getControllerId());
                game.getState().addOtherAbility(card, ability);
            } else {
                discard();
            }
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
            return "";
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
