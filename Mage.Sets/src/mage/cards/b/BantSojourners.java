package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BantSojourners extends CardImpl {

    public BantSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When you cycle Bant Sojourners or it dies, you may create a 1/1 white Soldier creature token.
        this.addAbility(new OrTriggeredAbility(Zone.ALL,
                new CreateTokenEffect(new SoldierToken()),
                true,
                "When you cycle {this} or it dies, ",
                new CycleTriggeredAbility(null, true),
                new DiesSourceTriggeredAbility(null, true)
        ));

        // Cycling {2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));
    }

    private BantSojourners(final BantSojourners card) {
        super(card);
    }

    @Override
    public BantSojourners copy() {
        return new BantSojourners(this);
    }
}
