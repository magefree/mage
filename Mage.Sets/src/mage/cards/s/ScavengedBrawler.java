package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScavengedBrawler extends CardImpl {

    public ScavengedBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {5}, Exile Scavenged Brawler from your graveyard: Choose target creature. Put four +1/+1 counters, a flying counter, a vigilance counter, a trample counter, and a lifelink counter on that creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(4))
                        .setText("choose target creature. Put four +1/+1 counters"),
                new GenericManaCost(5)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new AddCountersTargetEffect(
                CounterType.FLYING.createInstance()
        ).setText(", a flying counter"));
        ability.addEffect(new AddCountersTargetEffect(
                CounterType.VIGILANCE.createInstance()
        ).setText(", a vigilance counter"));
        ability.addEffect(new AddCountersTargetEffect(
                CounterType.TRAMPLE.createInstance()
        ).setText(", a trample counter"));
        ability.addEffect(new AddCountersTargetEffect(
                CounterType.LIFELINK.createInstance()
        ).setText(", and a lifelink counter on that creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ScavengedBrawler(final ScavengedBrawler card) {
        super(card);
    }

    @Override
    public ScavengedBrawler copy() {
        return new ScavengedBrawler(this);
    }
}
