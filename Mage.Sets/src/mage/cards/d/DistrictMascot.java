package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DistrictMascot extends CardImpl {

    public DistrictMascot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // This creature enters with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                "with a +1/+1 counter on it"
        ));

        // {1}{G}, Remove two +1/+1 counters from this creature: Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // Whenever this creature attacks while saddled, put a +1/+1 counter on it.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it")
        ));

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private DistrictMascot(final DistrictMascot card) {
        super(card);
    }

    @Override
    public DistrictMascot copy() {
        return new DistrictMascot(this);
    }
}
