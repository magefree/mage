
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.GoldmeadowHarrierToken;

/**
 *
 * @author fireshoes
 */
public final class GoldmeadowLookout extends CardImpl {

    public GoldmeadowLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, {tap}, Discard a card: Create a 1/1 white Kithkin Soldier creature token named Goldmeadow Harrier. It has "{W}, {tap}: Tap target creature."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GoldmeadowHarrierToken()), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private GoldmeadowLookout(final GoldmeadowLookout card) {
        super(card);
    }

    @Override
    public GoldmeadowLookout copy() {
        return new GoldmeadowLookout(this);
    }
}
