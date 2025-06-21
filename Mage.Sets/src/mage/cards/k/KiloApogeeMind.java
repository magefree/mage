package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KiloApogeeMind extends CardImpl {

    public KiloApogeeMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Kilo becomes tapped, proliferate.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new ProliferateEffect()));
    }

    private KiloApogeeMind(final KiloApogeeMind card) {
        super(card);
    }

    @Override
    public KiloApogeeMind copy() {
        return new KiloApogeeMind(this);
    }
}
