

package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class GoldmeadowHarrierToken extends TokenImpl {

    public GoldmeadowHarrierToken() {
        super("Goldmeadow Harrier", "1/1 white Kithkin Soldier creature token named Goldmeadow Harrier. It has \"{W}, {T}: Tap target creature.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KITHKIN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public GoldmeadowHarrierToken(final GoldmeadowHarrierToken token) {
        super(token);
    }

    public GoldmeadowHarrierToken copy() {
        return new GoldmeadowHarrierToken(this);
    }
}
