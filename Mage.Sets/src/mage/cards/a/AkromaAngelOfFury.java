
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class AkromaAngelOfFury extends CardImpl {

    public AkromaAngelOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Akroma, Angel of Fury can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // protection from white and from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE, ObjectColor.BLUE));
        // {R}: Akroma, Angel of Fury gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
        // Morph {3}{R}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{R}{R}{R}")));
    }

    private AkromaAngelOfFury(final AkromaAngelOfFury card) {
        super(card);
    }

    @Override
    public AkromaAngelOfFury copy() {
        return new AkromaAngelOfFury(this);
    }
}
