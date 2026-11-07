package mage.abilities.keyword;

import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityCastMode;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * Cast from the graveyard for the listed mana cost and return a creature you
 * control to its owner's hand. Exile the spell when it leaves the stack.
 *
 * @author muz
 */
public class BeamMeUpAbility extends CastFromGraveyardAbility {

    public BeamMeUpAbility(Card card, String manaString) {
        super(card, new ManaCostsImpl<>(manaString), SpellAbilityCastMode.BEAM_ME_UP);
        this.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledCreaturePermanent()));
    }

    private BeamMeUpAbility(final BeamMeUpAbility ability) {
        super(ability);
    }

    @Override
    public BeamMeUpAbility copy() {
        return new BeamMeUpAbility(this);
    }

    @Override
    public String getRule() {
        return name + " <i>(You may cast this card from your graveyard for " + getManaCosts().getText()
            + " if you also return a creature you control to its owner's hand. Then exile this spell.)</i>";
    }
}
