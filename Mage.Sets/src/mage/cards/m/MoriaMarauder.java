package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoriaMarauder extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a Goblin or Orc you control");

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.ORC.getPredicate()
        ));
    }

    public MoriaMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever a Goblin or Orc you control deals combat damage to a player, exile the top card of your library. You may play that card this turn.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1), filter,
                false, SetTargetPointer.NONE, true
        ));
    }

    private MoriaMarauder(final MoriaMarauder card) {
        super(card);
    }

    @Override
    public MoriaMarauder copy() {
        return new MoriaMarauder(this);
    }
}
