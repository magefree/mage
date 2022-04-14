package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MetropolisAngel extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public MetropolisAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you attack with one or more creatures with counters on them, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1), 1, filter
        ));
    }

    private MetropolisAngel(final MetropolisAngel card) {
        super(card);
    }

    @Override
    public MetropolisAngel copy() {
        return new MetropolisAngel(this);
    }
}
