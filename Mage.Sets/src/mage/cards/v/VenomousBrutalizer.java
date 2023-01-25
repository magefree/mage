package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class VenomousBrutalizer extends CardImpl {

    public VenomousBrutalizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Toxic 3
        this.addAbility(new ToxicAbility(3));

        // When Venomous Brutalizer enters the battlefield, you may pay {1}{G}. If you do, proliferate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new ProliferateEffect(), new ManaCostsImpl<>("{1}{G}"))
        ));
    }

    private VenomousBrutalizer(final VenomousBrutalizer card) {
        super(card);
    }

    @Override
    public VenomousBrutalizer copy() {
        return new VenomousBrutalizer(this);
    }
}
