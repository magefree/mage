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

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class TreasureToken extends TokenImpl {

    public TreasureToken() {
        super("Treasure Token", "Treasure token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TREASURE);

        // {T}, Sacrifice this artifact: Add one mana of any color.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        availableImageSetCodes = Arrays.asList("XLN", "RNA", "M20", "C19", "C20", "M21", "CMR", "KHM", "STX", "MH2", "AFR", "VOW", "NEO", "SLD", "2XM");
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
