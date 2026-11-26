package mage.abilities.common;

import mage.MageIdentifier;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.Set;
import java.util.UUID;

/**
 * @author weirddan455, JayDi85, notgreat
 */
public class SpellTransformedAbility extends SpellAbility {

    protected final String manaCost; //This variable is only used for rules text

    public SpellTransformedAbility(Card card, String manaCost) {
        super(card.getSecondFaceSpellAbility());
        this.newId();

        // verify check: make sure targets setup of second side done before alternative spell create, e.g. disturb on aura
        if (card.getSecondCardFace().hasSubtype(SubType.AURA, null) 
              && this.getTargets().isEmpty()) {
            // how-to fix: add left spell ability after second side targets setup
            throw new IllegalArgumentException("Wrong code usage: transformed spell ability must be added after the aura target of the second face - " + card.getName());
        }

        // getSecondFaceSpellAbility() already verified that second face exists
        this.setCardName(card.getSecondCardFace().getName());

        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.setSpellAbilityCastMode(SpellAbilityCastMode.TRANSFORMED);

        this.manaCost = manaCost;
        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaCost));
    }

    public SpellTransformedAbility(final SpellAbility ability) {
        super(ability);
        this.newId();

        this.manaCost = null;
        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();

        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.setSpellAbilityCastMode(SpellAbilityCastMode.TRANSFORMED);
    }

    protected SpellTransformedAbility(final SpellTransformedAbility ability) {
        super(ability);
        this.manaCost = ability.manaCost;
    }

    @Override
    public SpellTransformedAbility copy() {
        return new SpellTransformedAbility(this);
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        if (super.activate(game, allowedIdentifiers, noMana)) {
            final Card card = game.getCard(this.getSourceId());
            if (card == null) {
                return false;
            }
            game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + getSourceId() + card.getZoneChangeCounter(game), Boolean.TRUE);
            return true;
        }
        return false;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivationStatus.getFalse();
    }
}
