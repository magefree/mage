package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class FinFangFoom extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");
    private static final FilterSpell spellFilter = new FilterInstantOrSorcerySpell(
            "instant or sorcery spell that targets an artifact or land"
    );

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.LAND.getPredicate()
        ));
        spellFilter.add(new TargetsPermanentPredicate(filter));
    }

    public FinFangFoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell that targets an artifact or land, copy that spell. You may choose new targets for the copy. Put two +1/+1 counters on Fin Fang Foom.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new CopyTargetStackObjectEffect(true).withText("that spell"), spellFilter,
            false, SetTargetPointer.SPELL
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        this.addAbility(ability);
    }

    private FinFangFoom(final FinFangFoom card) {
        super(card);
    }

    @Override
    public FinFangFoom copy() {
        return new FinFangFoom(this);
    }
}
