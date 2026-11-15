package mage.cards.v;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author muz
 */
public final class VigorbloomCharm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VigorbloomCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}");

        // Choose one --
        // * Target permanent you control gains hexproof and indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
            .setText("target permanent you control gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
            .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledPermanent());

        // * You draw a card and gain 3 life.
        this.getSpellAbility().addMode(new Mode(
            new DrawCardSourceControllerEffect(1, true)
        ).addEffect(
            new GainLifeEffect(3).setText("and gain 3 life")
        ));

        // * Put a +1/+1 counter on target creature you control. Then it fights target creature an opponent controls.
        this.getSpellAbility().addMode(new Mode(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        ).addEffect(
            new FightTargetsEffect().setText("Then it fights target creature an opponent controls")
        ).addTarget(
            new TargetControlledCreaturePermanent()
        ).addTarget(
            new TargetPermanent(filter)
        ));
    }

    private VigorbloomCharm(final VigorbloomCharm card) {
        super(card);
    }

    @Override
    public VigorbloomCharm copy() {
        return new VigorbloomCharm(this);
    }
}
