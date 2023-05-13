package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.PlantToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhylathWorldSculptor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("basic land you control");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.PLANT);

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);

    public PhylathWorldSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Phylath, World Sculptor enters the battlefield, create a 0/1 green Plant creature token for each basic land you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PlantToken(), xValue)));

        // Landfall — Whenever a land enters the battlefield under your control, put four +1/+1 counters on target Plant you control.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private PhylathWorldSculptor(final PhylathWorldSculptor card) {
        super(card);
    }

    @Override
    public PhylathWorldSculptor copy() {
        return new PhylathWorldSculptor(this);
    }
}
