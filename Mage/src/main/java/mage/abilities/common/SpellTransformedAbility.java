package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author weirddan455, JayDi85, notgreat
 */
public class SpellTransformedAbility extends SpellAbility {

    protected final String manaCost; //This variable is only used for rules text

    public SpellTransformedAbility(Card card, String manaCost) {
        super(card.getSecondFaceSpellAbility());
        this.newId();

        // getSecondFaceSpellAbility() already verified that second face exists
        this.setCardName(card.getSecondCardFace().getName());

        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.setSpellAbilityCastMode(SpellAbilityCastMode.TRANSFORMED);

        this.manaCost = manaCost;
        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaCost));
        this.addSubAbility(new TransformAbility());
    }

    public SpellTransformedAbility(final SpellAbility ability) {
        super(ability);
        this.newId();

        this.manaCost = null;
        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();

        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.setSpellAbilityCastMode(SpellAbilityCastMode.TRANSFORMED);
        //when casting this way, the card must have the TransformAbility from elsewhere
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
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getSourceId(), Boolean.TRUE);
            // TODO: must be removed after transform cards (one side) migrated to MDF engine (multiple sides)
            TransformedEffect effect = new TransformedEffect();
            game.addEffect(effect, this);
            effect.apply(game, this); //Apply the effect immediately
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

class TransformedEffect extends ContinuousEffectImpl {

    public TransformedEffect() {
        super(Duration.WhileOnStack, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.BecomeCreature);
        staticText = "";
    }

    private TransformedEffect(final TransformedEffect effect) {
        super(effect);
    }

    @Override
    public TransformedEffect copy() {
        return new TransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getSourceId());
        if (spell == null || spell.getCard().getSecondCardFace() == null) {
            return false;
        }
        // simulate another side as new card (another code part in spell constructor)
        TransformAbility.transformCardSpellDynamic(spell, spell.getCard().getSecondCardFace(), game);
        return true;
    }
}
