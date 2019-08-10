package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

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
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), StaticFilters.FILTER_SPELL, false, false);
        this.rule = "When you cast your commander, return {this} from your graveyard to the battlefield.";
    }

    private SkyfirePhoenixTriggeredAbility(final SkyfirePhoenixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game) && game.getCommandersIds(
                game.getPlayer(getControllerId()), CommanderCardType.COMMANDER_OR_OATHBREAKER
        ).contains(event.getTargetId());
    }

    @Override
    public SkyfirePhoenixTriggeredAbility copy() {
        return new SkyfirePhoenixTriggeredAbility(this);
    }
}