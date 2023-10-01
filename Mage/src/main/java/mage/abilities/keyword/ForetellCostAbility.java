package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.ModalDoubleFacedCard;
import mage.cards.SplitCard;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;

import java.util.UUID;

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
        this.addCost(new ManaCostsImpl<>(foretellCost));
    }

    protected ForetellCostAbility(final ForetellCostAbility ability) {
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
                } else if (card instanceof ModalDoubleFacedCard) {
                    if (((ModalDoubleFacedCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        return ((ModalDoubleFacedCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((ModalDoubleFacedCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        return ((ModalDoubleFacedCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
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
                } else if (card instanceof ModalDoubleFacedCard) {
                    if (((ModalDoubleFacedCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((ModalDoubleFacedCard) card).getLeftHalfCard().getSpellAbility().copy();
                    } else if (((ModalDoubleFacedCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((ModalDoubleFacedCard) card).getRightHalfCard().getSpellAbility().copy();
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
                spellAbilityCopy.clearManaCosts();
                spellAbilityCopy.clearManaCostsToPay();
                spellAbilityCopy.addCost(this.getCosts().copy());
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
        if (!getCosts().isEmpty()) {
            sbRule.append("&mdash;");
        } else {
            sbRule.append(' ');
        }
        if (!getManaCosts().isEmpty()) {
            sbRule.append(getManaCosts().getText());
        }
        if (!getCosts().isEmpty()) {
            if (!getManaCosts().isEmpty()) {
                sbRule.append(", ");
            }
            sbRule.append(getCosts().getText());
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
