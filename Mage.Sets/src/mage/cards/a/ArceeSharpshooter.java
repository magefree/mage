package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArceeSharpshooter extends CardImpl {

    public ArceeSharpshooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.a.ArceeAcrobaticCoupe.class;

        // More Than Meets the Eye {R}{W}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{R}{W}"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {1}, Remove one or more +1/+1 counters from Arcee: It deals that much damage to target creature. Convert Arcee.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(GetXValue.instance)
                        .setText("it deals that much damage to target creature"),
                new GenericManaCost(1)
        );
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1, 1));
        ability.addEffect(new TransformSourceEffect().setText("convert {this}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ArceeSharpshooter(final ArceeSharpshooter card) {
        super(card);
    }

    @Override
    public ArceeSharpshooter copy() {
        return new ArceeSharpshooter(this);
    }
}
