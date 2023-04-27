package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.TargetSpell;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class MyojinOfCrypticDreams extends CardImpl {

    private static final FilterSpell permanentSpellFilter = new FilterSpell("permanent spell you control");

    static {
        permanentSpellFilter.add(TargetController.YOU.getControllerPredicate());
        permanentSpellFilter.add(PermanentPredicate.instance);
    }

    public MyojinOfCrypticDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Myojin of Cryptic Dreams enters the battlefield with an indestructible counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()),
                CastFromHandSourcePermanentCondition.instance, null,
                "with an indestructible counter on it if you cast it from your hand"
        ), new CastFromHandWatcher());

        // Remove an indestructible counter from Myojin of Cryptic Dreams:
        // Copy target permanent spell you control three times. (The copies become tokens.)
        Ability ability = new SimpleActivatedAbility(
                new CopyTargetSpellEffect(false, false, false)
                        .setText("Copy target permanent spell you control three times. <i>(The copies become tokens.)</i>"),
                new RemoveCountersSourceCost(CounterType.INDESTRUCTIBLE.createInstance())
        );
        ability.addEffect(new CopyTargetSpellEffect(false, false, false).setText(" "));
        ability.addEffect(new CopyTargetSpellEffect(false, false, false).setText(" "));
        ability.addTarget(new TargetSpell(permanentSpellFilter));
        this.addAbility(ability);
    }

    private MyojinOfCrypticDreams(final MyojinOfCrypticDreams card) {
        super(card);
    }

    @Override
    public MyojinOfCrypticDreams copy() {
        return new MyojinOfCrypticDreams(this);
    }
}
