package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoastlineMarauders extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public CoastlineMarauders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Coastline Marauders attacks, it gets +1/+0 until end of turn for each land defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn
        ).setText("it gets +1/+0 until end of turn for each land defending player controls"), false));

        // Encore {4}{R}{R}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{4}{R}{R}")));
    }

    private CoastlineMarauders(final CoastlineMarauders card) {
        super(card);
    }

    @Override
    public CoastlineMarauders copy() {
        return new CoastlineMarauders(this);
    }
}
