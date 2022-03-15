package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class BloodToken extends TokenImpl {

    public BloodToken() {
        super("Blood Token", "Blood token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.BLOOD);

        // {1}, {T}, Discard a card, Sacrifice this artifact: Draw a card.”
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addCost(new SacrificeSourceCost().setText("Sacrifice this artifact"));
        this.addAbility(ability);

        availableImageSetCodes = Arrays.asList("VOW");
    }

    public BloodToken(final BloodToken token) {
        super(token);
    }

    public BloodToken copy() {
        return new BloodToken(this);
    }
}
