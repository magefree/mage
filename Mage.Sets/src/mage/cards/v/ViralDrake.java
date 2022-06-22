package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author North
 */
public final class ViralDrake extends CardImpl {

    public ViralDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Infect (This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.addAbility(InfectAbility.getInstance());

        // {3}{U}: Proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProliferateEffect(), new ManaCostsImpl<>("{3}{U}")));
    }

    private ViralDrake(final ViralDrake card) {
        super(card);
    }

    @Override
    public ViralDrake copy() {
        return new ViralDrake(this);
    }
}
