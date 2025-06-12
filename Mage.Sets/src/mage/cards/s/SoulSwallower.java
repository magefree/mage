package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SoulSwallower extends CardImpl {

    public SoulSwallower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // <i>Delirium</i> &mdash; At the beginning of your upkeep, if there are four or more card types among cards in your graveyard, put three +1/+1 counters on Soul Swallower.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)))
                .withInterveningIf(DeliriumCondition.instance).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private SoulSwallower(final SoulSwallower card) {
        super(card);
    }

    @Override
    public SoulSwallower copy() {
        return new SoulSwallower(this);
    }
}
