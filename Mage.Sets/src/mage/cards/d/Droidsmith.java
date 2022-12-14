package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class Droidsmith extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(SubType.DROID.getPredicate());
    }
    public Droidsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");
        this.addSubType(SubType.ANGEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Droidsmith can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        //Droid creatures you control get +1/+0 as long as Droidsmith is tapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter),
                new CompoundCondition(SourceTappedCondition.TAPPED),
                "Droid creatures you control get +1/+0 as long as Droidsmith is tapped."
        )));
        //Droid creatures you control get +0/+1 as long as Droidsmith is untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filter),
                new CompoundCondition(SourceTappedCondition.UNTAPPED),
                "Droid creatures you control get +0/+1 as long as Droidsmith is untapped."
        )));
    }

    public Droidsmith(final Droidsmith card) {
        super(card);
    }

    @Override
    public Droidsmith copy() {
        return new Droidsmith(this);
    }
}
