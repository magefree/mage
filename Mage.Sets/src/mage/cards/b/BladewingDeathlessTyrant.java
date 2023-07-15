package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieKnightToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class BladewingDeathlessTyrant extends CardImpl {

    public BladewingDeathlessTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        //  Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Bladewing, Deathless Tyrant deals combat damage to a player or planeswalker,
        // for each creature card in your graveyard, create a 2/2 black Zombie Knight creature token with menace.
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                new CreateTokenEffect(new ZombieKnightToken(),
                        new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)), false));
    }

    private BladewingDeathlessTyrant(final BladewingDeathlessTyrant card) {
        super(card);
    }

    @Override
    public BladewingDeathlessTyrant copy() {
        return new BladewingDeathlessTyrant(this);
    }
}
