
package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public final class ClueArtifactToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("SOI", "EDM"));
    }

    public ClueArtifactToken() {
        super("Clue", "colorless Clue artifact token with \"{2}, Sacrifice this artifact: Draw a card.\"");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.CLUE);

        // {2}, Sacrifice this artifact: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        SacrificeSourceCost cost = new SacrificeSourceCost();
        cost.setText("Sacrifice this artifact");
        ability.addCost(cost);
        this.addAbility(ability);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("SOI")) {
            this.setTokenType(RandomUtil.nextInt(6) + 1); // 6 different images
        }
        if (getOriginalExpansionSetCode().equals("EDM")) {
            this.setTokenType(RandomUtil.nextInt(6) + 1); // 6 different images
        }
    }

    public ClueArtifactToken(final ClueArtifactToken token) {
        super(token);
    }

    @Override
    public ClueArtifactToken copy() {
        return new ClueArtifactToken(this);
    }
}
