package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 */
public final class ClueArtifactToken extends TokenImpl {

    public ClueArtifactToken() {
        super("Clue Token", "colorless Clue artifact token with \"{2}, Sacrifice this artifact: Draw a card.\"");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.CLUE);

        // {2}, Sacrifice this artifact: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        SacrificeSourceCost cost = new SacrificeSourceCost();
        cost.setText("Sacrifice this artifact");
        ability.addCost(cost);
        this.addAbility(ability);
    }

    public ClueArtifactToken(final ClueArtifactToken token) {
        super(token);
    }

    @Override
    public ClueArtifactToken copy() {
        return new ClueArtifactToken(this);
    }
}
