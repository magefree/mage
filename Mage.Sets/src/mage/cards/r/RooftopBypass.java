package mage.cards.r;

import java.util.UUID;

import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AssassinMenaceToken;

/**
 *
 * @author grimreap124
 */
public final class RooftopBypass extends CardImpl {

    public RooftopBypass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{B}");
        

        // Whenever one or more nontoken creatures you control deal combat damage to a player, create a 1/1 black Assassin creature token with menace.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new CreateTokenEffect(new AssassinMenaceToken()),
                StaticFilters.FILTER_CONTROLLED_CREATURES_NON_TOKEN));
    }

    private RooftopBypass(final RooftopBypass card) {
        super(card);
    }

    @Override
    public RooftopBypass copy() {
        return new RooftopBypass(this);
    }
}
