
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Mycoloth extends CardImpl {

    public Mycoloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Devour 2 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(2));

        // At the beginning of your upkeep, create a 1/1 green Saproling creature token for each +1/+1 counter on Mycoloth.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken(),new CountersSourceCount(CounterType.P1P1)),
                TargetController.YOU,
                false
            ));
    }

    private Mycoloth(final Mycoloth card) {
        super(card);
    }

    @Override
    public Mycoloth copy() {
        return new Mycoloth(this);
    }
}
