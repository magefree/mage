package mage.cards.j;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JailbreakScheme extends CardImpl {

    public JailbreakScheme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {3} -- Put a +1/+1 counter on target creature. It can't be blocked this turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect().setText("it can't be blocked this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(3));

        // + {2} -- Target artifact or creature's owner puts it on the top or bottom of their library.
        this.getSpellAbility().addMode(new Mode(new PutOnTopOrBottomLibraryTargetEffect(false))
                .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE))
                .withCost(new GenericManaCost(2)));
    }

    private JailbreakScheme(final JailbreakScheme card) {
        super(card);
    }

    @Override
    public JailbreakScheme copy() {
        return new JailbreakScheme(this);
    }
}
