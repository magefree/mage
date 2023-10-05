package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyfirePhoenix extends CardImpl {

    public SkyfirePhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When you cast your commander, return Skyfire Phoenix from your graveyard to the battlefield.
        this.addAbility(new SkyfirePhoenixTriggeredAbility());
    }

    private SkyfirePhoenix(final SkyfirePhoenix card) {
        super(card);
    }

    @Override
    public SkyfirePhoenix copy() {
        return new SkyfirePhoenix(this);
    }
}

class SkyfirePhoenixTriggeredAbility extends SpellCastControllerTriggeredAbility {

    SkyfirePhoenixTriggeredAbility() {
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(false, false),
                StaticFilters.FILTER_SPELL, false, SetTargetPointer.NONE);
        setTriggerPhrase("When you cast your commander, ");
    }

    private SkyfirePhoenixTriggeredAbility(final SkyfirePhoenixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }

        Player controller = game.getPlayer(getControllerId());
        if (controller == null) {
            return false;
        }

        // must check all parts (example: cast one from from mdf/split card)
        return game.getCommandersIds(controller, CommanderCardType.COMMANDER_OR_OATHBREAKER, true).contains(event.getSourceId());
    }

    @Override
    public SkyfirePhoenixTriggeredAbility copy() {
        return new SkyfirePhoenixTriggeredAbility(this);
    }
}
