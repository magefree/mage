
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldOrCommandZoneCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.EdgarMarkovToken;

/**
 *
 * @author TheElk801
 */
public final class EdgarMarkov extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Vampire you control");
    private static final FilterSpell filter2 = new FilterSpell("another Vampire spell");//"another" is just there for templating, doesn't affect the card itself

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
        filter2.add(SubType.VAMPIRE.getPredicate());
    }

    public EdgarMarkov(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Eminence - Whenever you cast another Vampire spell, if Edgar Markov is in the command zone or on the battlefield, create a 1/1 black Vampire creature token.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(Zone.ALL, new CreateTokenEffect(new EdgarMarkovToken()), filter2, false, false),
                SourceOnBattlefieldOrCommandZoneCondition.instance,
                "Whenever you cast another Vampire spell, if {this} is in the command zone or on the battlefield, create a 1/1 black Vampire creature token.");
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Edgar Markov attacks, put a +1/+1 counter on each Vampire you control.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false));
    }

    private EdgarMarkov(final EdgarMarkov card) {
        super(card);
    }

    @Override
    public EdgarMarkov copy() {
        return new EdgarMarkov(this);
    }
}
