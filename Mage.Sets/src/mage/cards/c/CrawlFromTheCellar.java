package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author weirddan455
 */
public final class CrawlFromTheCellar extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.ZOMBIE);

    public CrawlFromTheCellar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Return target creature card from your graveyard to your hand. Put a +1/+1 counter on up to one target Zombie you control.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setTargetPointer(SecondTargetPointer.getInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1, filter, false));

        // Flashback {3}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{B}")));
    }

    private CrawlFromTheCellar(final CrawlFromTheCellar card) {
        super(card);
    }

    @Override
    public CrawlFromTheCellar copy() {
        return new CrawlFromTheCellar(this);
    }
}
