
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;

/**
 *
 * @author LevelX2
 */
public final class CentaurVinecrasher extends CardImpl {

    public CentaurVinecrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.CENTAUR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Centaur Vinecrasher enters the battlefield with a number of +1/+1 counters on it equal to the number of land cards in all graveyards.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new CardsInAllGraveyardsCount(new FilterLandCard()), true);
        effect.setText("with a number of +1/+1 counters on it equal to the number of land cards in all graveyards");
        this.addAbility(new EntersBattlefieldAbility(effect));
        // Whenever a land card is put into a graveyard from anywhere, you may pay {G}{G}. If you do, return Centaur Vinecrasher from your graveyard to your hand.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(Zone.GRAVEYARD,
                new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{G}{G}")),
                false, new FilterLandCard("a land card"), TargetController.ANY, SetTargetPointer.NONE
        ));
    }

    private CentaurVinecrasher(final CentaurVinecrasher card) {
        super(card);
    }

    @Override
    public CentaurVinecrasher copy() {
        return new CentaurVinecrasher(this);
    }
}
