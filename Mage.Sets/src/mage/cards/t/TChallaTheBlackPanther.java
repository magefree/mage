package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.VibraniumToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class TChallaTheBlackPanther extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact spell with mana value 4 or greater");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    public TChallaTheBlackPanther(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever T'Challa enters or attacks, create a tapped Vibranium token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new CreateTokenEffect(new VibraniumToken(), 1, true)
        ));

        // Whenever you cast an artifact spell with mana value 4 or greater, put two +1/+1 counters on T'Challa.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), filter, false
        ));
    }

    private TChallaTheBlackPanther(final TChallaTheBlackPanther card) {
        super(card);
    }

    @Override
    public TChallaTheBlackPanther copy() {
        return new TChallaTheBlackPanther(this);
    }
}
