package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Boar2Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarefreeSwinemaster extends CardImpl {

    public CarefreeSwinemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Carefree Swinemaster attacks, you may pay {1}{G}. If you do, create a 2/2 green Boar creature token that's tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(
                new Boar2Token(), 1, true, true
        ), new ManaCostsImpl<>("{1}{G}"))));
    }

    private CarefreeSwinemaster(final CarefreeSwinemaster card) {
        super(card);
    }

    @Override
    public CarefreeSwinemaster copy() {
        return new CarefreeSwinemaster(this);
    }
}
