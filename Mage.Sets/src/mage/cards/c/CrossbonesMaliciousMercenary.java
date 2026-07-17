package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CrossbonesMaliciousMercenary extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.VILLAIN, "another Villain you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CrossbonesMaliciousMercenary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another Villain you control enters, put a +1/+1 counter on Crossbones. He deals 2 damage to each opponent. This ability triggers only once each turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ).setTriggersLimitEachTurn(1);
        ability.addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT));
        this.addAbility(ability);
    }

    private CrossbonesMaliciousMercenary(final CrossbonesMaliciousMercenary card) {
        super(card);
    }

    @Override
    public CrossbonesMaliciousMercenary copy() {
        return new CrossbonesMaliciousMercenary(this);
    }
}
