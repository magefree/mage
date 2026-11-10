package mage.cards.s;

import mage.MageInt;
import mage.abilities.AbilityImpl;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author muz
 */
public final class StoriedVeteran extends CardImpl {

    public StoriedVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // This creature can't block.
        this.addAbility(new CantBlockAbility());

        // You may cast this card from your graveyard. If you do, it enters with a finality counter on it.
        AbilityImpl ability = new MayCastFromGraveyardSourceAbility();
        ability.appendToRule(" If you do, it enters with a finality counter on it.");
        ability.addSubAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.FINALITY.createInstance()),
            CastFromGraveyardSourceCondition.instance, "", "").setRuleVisible(false));
        this.addAbility(ability);
    }

    private StoriedVeteran(final StoriedVeteran card) {
        super(card);
    }

    @Override
    public StoriedVeteran copy() {
        return new StoriedVeteran(this);
    }
}
