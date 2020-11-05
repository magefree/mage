package mage.cards.g;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuildlessCommons extends CardImpl {

    public GuildlessCommons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Guildless Commons enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Guildless Commons enters the battlefield, return a land you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND), false));

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    private GuildlessCommons(final GuildlessCommons card) {
        super(card);
    }

    @Override
    public GuildlessCommons copy() {
        return new GuildlessCommons(this);
    }
}
