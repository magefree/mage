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

import java.util.Arrays;

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

        availableImageSetCodes = Arrays.asList("C18", "SOI", "MH2", "AFC", "MID", "VOC", "SLD", "2XM", "NCC", "CLB", "40K");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("SOI")) {
            this.setTokenType(RandomUtil.nextInt(6) + 1); // 6 different images
        }

        if (getOriginalExpansionSetCode().equals("MH2")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1); // 2 different images
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
