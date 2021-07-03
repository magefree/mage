package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class TreasureToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    public TreasureToken() {
        super("Treasure", "Treasure token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TREASURE);
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        availableImageSetCodes = Arrays.asList("XLN", "RNA", "M20", "C19", "C20", "M21", "CMR", "KHM", "STX");
    }

    public TreasureToken(final TreasureToken token) {
        super(token);
    }

    public TreasureToken copy() {
        return new TreasureToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("XLN")) {
            this.setTokenType(RandomUtil.nextInt(4) + 1);
        }
    }
}
