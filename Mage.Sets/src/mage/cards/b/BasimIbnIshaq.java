package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasimIbnIshaq extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public BasimIbnIshaq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a historic spell, draw a card. Basim Ibn Ishaq can't be blocked this turn. This ability triggers only once each turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ).setTriggersLimitEachTurn(1);
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability);

        // Whenever Basim Ibn Ishaq deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));
    }

    private BasimIbnIshaq(final BasimIbnIshaq card) {
        super(card);
    }

    @Override
    public BasimIbnIshaq copy() {
        return new BasimIbnIshaq(this);
    }
}
