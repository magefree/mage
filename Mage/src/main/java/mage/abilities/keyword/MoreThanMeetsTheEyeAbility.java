package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author weirddan455, JayDi85, TheElk801 (based heavily on disturb)
 */
public class MoreThanMeetsTheEyeAbility extends SpellAbility {

    private final String manaCost;
    private SpellAbility spellAbilityToResolve;

    public MoreThanMeetsTheEyeAbility(Card card, String manaCost) {
        super(card.getSecondFaceSpellAbility());
        this.newId();

        // getSecondFaceSpellAbility() already verified that second face exists
        this.setCardName(card.getSecondCardFace().getName());

        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.setSpellAbilityCastMode(SpellAbilityCastMode.MORE_THAN_MEETS_THE_EYE);

        this.manaCost = manaCost;
        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();
        this.addManaCost(new ManaCostsImpl<>(manaCost));
        this.addSubAbility(new TransformAbility());
    }

    private MoreThanMeetsTheEyeAbility(final MoreThanMeetsTheEyeAbility ability) {
        super(ability);
        this.manaCost = ability.manaCost;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public MoreThanMeetsTheEyeAbility copy() {
        return new MoreThanMeetsTheEyeAbility(this);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getSourceId(), Boolean.TRUE);
            // TODO: must be removed after transform cards (one side) migrated to MDF engine (multiple sides)
            game.addEffect(new MoreThanMeetsTheEyeEffect(), this);
            return true;
        }
        return false;
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "More Than Meets the Eye " + this.manaCost
            + " <i>(You may cast this card converted for " + this.manaCost + ".)</i>";
    }
}

class MoreThanMeetsTheEyeEffect extends ContinuousEffectImpl {

    public MoreThanMeetsTheEyeEffect() {
        super(Duration.WhileOnStack, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.BecomeCreature);
        staticText = "";
    }

    private MoreThanMeetsTheEyeEffect(final MoreThanMeetsTheEyeEffect effect) {
        super(effect);
    }

    @Override
    public MoreThanMeetsTheEyeEffect copy() {
        return new MoreThanMeetsTheEyeEffect(this);
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
