package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.Kraken99Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpawningKraken extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("a Kraken, Leviathan, Octopus, or Serpent you control");

    static {
        filter.add(Predicates.or(
                SubType.KRAKEN.getPredicate(),
                SubType.LEVIATHAN.getPredicate(),
                SubType.OCTOPUS.getPredicate(),
                SubType.SERPENT.getPredicate()
        ));
    }

    public SpawningKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever a Kraken, Leviathan, Octopus, or Serpent you control deals combat damage to a player, create a 9/9 blue Kraken creature token.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new Kraken99Token()), filter,
                false, SetTargetPointer.NONE, true
        ));
    }

    private SpawningKraken(final SpawningKraken card) {
        super(card);
    }

    @Override
    public SpawningKraken copy() {
        return new SpawningKraken(this);
    }
}
