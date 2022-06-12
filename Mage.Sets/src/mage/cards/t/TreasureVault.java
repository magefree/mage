package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class TreasureVault extends CardImpl {

    public TreasureVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {X}{X}, {T}, Sacrifice Treasure Vault: Create X Treasure tokens.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new TreasureToken(), ManacostVariableValue.REGULAR),
                new ManaCostsImpl<>("{X}{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TreasureVault(final TreasureVault card) {
        super(card);
    }

    @Override
    public TreasureVault copy() {
        return new TreasureVault(this);
    }
}
