package mage.cards.g;

import mage.abilities.common.AttacksOpponentWithMostLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuildArtisan extends CardImpl {

    public GuildArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever this creature attacks a player, if no other opponent has more life than that player, create two Treasure tokens."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new AttacksOpponentWithMostLifeTriggeredAbility(
                        new CreateTokenEffect(new TreasureToken(), 2), false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private GuildArtisan(final GuildArtisan card) {
        super(card);
    }

    @Override
    public GuildArtisan copy() {
        return new GuildArtisan(this);
    }
}
