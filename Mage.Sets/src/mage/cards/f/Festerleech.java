package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Festerleech extends CardImpl {

    public Festerleech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Festerleech deals combat damage to a player, you mill two cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new MillCardsControllerEffect(2), false
        ));

        // {1}{B}: Festerleech gets +2/+2 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        ));
    }

    private Festerleech(final Festerleech card) {
        super(card);
    }

    @Override
    public Festerleech copy() {
        return new Festerleech(this);
    }
}
