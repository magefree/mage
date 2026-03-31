package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverquillCharm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public SilverquillCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // Choose one --
        // * Put two +1/+1 counters on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("add counters"));

        // * Exile target creature with power 2 or less.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect())
                .addTarget(new TargetPermanent(filter).withChooseHint("exile")));

        // * Each opponent loses 3 life and you gain 3 life.
        this.getSpellAbility().addMode(new Mode(new LoseLifeOpponentsEffect(3))
                .addEffect(new GainLifeEffect(3).concatBy("and")));
    }

    private SilverquillCharm(final SilverquillCharm card) {
        super(card);
    }

    @Override
    public SilverquillCharm copy() {
        return new SilverquillCharm(this);
    }
}
