package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SazhKatzroy extends CardImpl {

    private static final FilterCard filter = new FilterCard("Bird or basic land card");

    static {
        filter.add(Predicates.or(
                SubType.BIRD.getPredicate(),
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                )
        ));
    }

    public SazhKatzroy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sazh Katzroy enters, you may search your library for a Bird or basic land card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Whenever Sazh Katzroy attacks, put counter on target creature, then double the number of +1/+1 counters on that creature.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new DoubleCountersTargetEffect(CounterType.P1P1)
                .setText(", then double the number of +1/+1 counters on that creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SazhKatzroy(final SazhKatzroy card) {
        super(card);
    }

    @Override
    public SazhKatzroy copy() {
        return new SazhKatzroy(this);
    }
}
