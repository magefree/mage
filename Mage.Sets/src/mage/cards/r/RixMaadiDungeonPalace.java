
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class RixMaadiDungeonPalace extends CardImpl {

    public RixMaadiDungeonPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}{B}{R}, {tap}: Each player discards a card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardEachPlayerEffect(), new ManaCostsImpl<>("{1}{B}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RixMaadiDungeonPalace(final RixMaadiDungeonPalace card) {
        super(card);
    }

    @Override
    public RixMaadiDungeonPalace copy() {
        return new RixMaadiDungeonPalace(this);
    }
}
