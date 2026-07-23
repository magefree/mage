package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author muz
 */
public final class HeroesForHire extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TREASURE, "Treasure");

    public HeroesForHire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // When this enchantment enters, create three Treasure tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new CreateTokenEffect(new TreasureToken(), 3)
        ));

        // Sacrifice a Treasure: Exile the top card of your library. You may play it this turn.
        Ability ability = new SimpleActivatedAbility(
            new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn).withTextOptions("it", true),
            new SacrificeTargetCost(filter)
        );
        this.addAbility(ability);
    }

    private HeroesForHire(final HeroesForHire card) {
        super(card);
    }

    @Override
    public HeroesForHire copy() {
        return new HeroesForHire(this);
    }
}
