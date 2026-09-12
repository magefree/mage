package mage.cards.k;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class KonstrariCharm extends CardImpl {

    public KonstrariCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Choose one --
        // * Konstrari Charm deals 6 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING).withChooseHint("deals 6 damage, with flying"));

        // * Put two +1/+1 counters on target creature. It gains trample until end of turn.
        this.getSpellAbility().addMode(new Mode(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)))
            .addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("It gains trample until end of turn")
            )
            .addTarget(new TargetCreaturePermanent())
        );

        // * Add {C}{C}{C}.
        this.getSpellAbility().addMode(new Mode(
            new BasicManaEffect(Mana.ColorlessMana(3))
        ));
    }

    private KonstrariCharm(final KonstrariCharm card) {
        super(card);
    }

    @Override
    public KonstrariCharm copy() {
        return new KonstrariCharm(this);
    }
}
