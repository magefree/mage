package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.BeholdType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinsbaileAspirant extends CardImpl {

    public KinsbaileAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast this spell, behold a Kithkin or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "behold a Kithkin or pay {2}", new BeholdCost(BeholdType.KITHKIN), new GenericManaCost(2)
        ));

        // Whenever another creature you control enters, this creature gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));
    }

    private KinsbaileAspirant(final KinsbaileAspirant card) {
        super(card);
    }

    @Override
    public KinsbaileAspirant copy() {
        return new KinsbaileAspirant(this);
    }
}
