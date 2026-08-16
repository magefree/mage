package mage.cards.n;

import java.util.UUID;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author muz
 */
public final class NecklaceOfGirion extends CardImpl {

    private static final FilterSpell greenFilter = new FilterSpell("a green spell");
    private static final FilterPermanent forestFilter = new FilterPermanent("a Forest");

    static {
        greenFilter.add(new ColorPredicate(ObjectColor.GREEN));
        forestFilter.add(SubType.FOREST.getPredicate());
    }

    public NecklaceOfGirion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever you cast a green spell and whenever a Forest you control enters, put a +1/+1 counter on target creature you control.
        Ability ability = new OrTriggeredAbility(
            Zone.BATTLEFIELD,
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            new SpellCastControllerTriggeredAbility(null, greenFilter, false),
            new EntersBattlefieldControlledTriggeredAbility(null, forestFilter)
        ).setTriggerPhrase("Whenever you cast a green spell and whenever a Forest you control enters, ");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private NecklaceOfGirion(final NecklaceOfGirion card) {
        super(card);
    }

    @Override
    public NecklaceOfGirion copy() {
        return new NecklaceOfGirion(this);
    }
}
