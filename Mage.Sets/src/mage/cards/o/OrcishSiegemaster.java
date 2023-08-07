package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OrcishSiegemaster extends CardImpl {

    private final static FilterControlledCreaturePermanent filterOrcAndGoblins =
            new FilterControlledCreaturePermanent("Orcs and Goblins");

    static {
        filterOrcAndGoblins.add(
                Predicates.or(
                        SubType.ORC.getPredicate(),
                        SubType.GOBLIN.getPredicate()
                )
        );
    }

    public OrcishSiegemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other Orcs and Goblins you control have trample.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        filterOrcAndGoblins,
                        true
                )
        ));

        // Whenever Orcish Siegemaster attacks, it gets +X/+0 until end of turn, where X is the greatest power among creatures you control.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(
                        GreatestPowerAmongControlledCreaturesValue.instance,
                        StaticValue.get(0), Duration.EndOfTurn, true, "it"
                )
        ));
    }

    private OrcishSiegemaster(final OrcishSiegemaster card) {
        super(card);
    }

    @Override
    public OrcishSiegemaster copy() {
        return new OrcishSiegemaster(this);
    }
}
