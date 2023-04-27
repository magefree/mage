package mage.cards.h;

import mage.ObjectColor;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HundredTalonStrike extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped white creature you control");
    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public HundredTalonStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");
        this.subtype.add(SubType.ARCANE);


        // Target creature gets +1/+0 and gains first strike until end of turn.
        Effect effect = new BoostTargetEffect(1,0, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("+1/+0 and first strike"));
        // Splice onto Arcane-Tap an untapped white creature you control.
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, new TapTargetCost(new TargetControlledCreaturePermanent(1,1,filter,false))));
    }

    private HundredTalonStrike(final HundredTalonStrike card) {
        super(card);
    }

    @Override
    public HundredTalonStrike copy() {
        return new HundredTalonStrike(this);
    }
}
