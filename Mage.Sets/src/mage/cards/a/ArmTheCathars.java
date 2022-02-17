package mage.cards.a;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;
import mage.target.targetpointer.ThirdTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmTheCathars extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent();

    static {
        filter2.add(new AnotherTargetPredicate(2));
        filter3.add(new AnotherTargetPredicate(3));
    }

    public ArmTheCathars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Until end of turn, target creature gets +3/+3, up to one other target creature gets +2/+2, and up to one other target creature gets +1/+1. Those creatures gain vigilance until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3)
                .setText("until end of turn, target creature gets +3/+3"));
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1.withChooseHint("+3/+3"));

        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setTargetPointer(new SecondTargetPointer())
                .setText(", up to one other target creature gets +2/+2"));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(0, 1, filter2, false);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2.withChooseHint("+2/+2"));

        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1)
                .setTargetPointer(new ThirdTargetPointer())
                .setText(", and up to one other target creature gets +1/+1"));
        TargetCreaturePermanent target3 = new TargetCreaturePermanent(0, 1, filter3, false);
        target3.setTargetTag(3);
        this.getSpellAbility().addTarget(target3.withChooseHint("+1/+1"));

        this.getSpellAbility().addEffect(
                new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setTargetPointer(new EachTargetPointer())
                .setText("Those creatures gain vigilance until end of turn"));
    }

    private ArmTheCathars(final ArmTheCathars card) {
        super(card);
    }

    @Override
    public ArmTheCathars copy() {
        return new ArmTheCathars(this);
    }
}
