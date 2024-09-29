package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MalametVeteran extends CardImpl {

    public MalametVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Descend 4 -- Whenever Malamet Veteran attacks, if there are four or more permanent cards in your graveyard, put a +1/+1 counter on target creature.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new AttacksTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())), DescendCondition.FOUR,
                "Whenever {this} attacks, if there are four or more permanent cards in your graveyard, put a +1/+1 counter on target creature."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.DESCEND_4).addHint(DescendCondition.getHint()));

    }

    private MalametVeteran(final MalametVeteran card) {
        super(card);
    }

    @Override
    public MalametVeteran copy() {
        return new MalametVeteran(this);
    }
}
