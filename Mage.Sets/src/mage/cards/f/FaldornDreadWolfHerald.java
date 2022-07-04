package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.WolfToken;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaldornDreadWolfHerald extends CardImpl {

    public FaldornDreadWolfHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell from exile or a land enters the battlefield under your control from exile, create a 2/2 green Wolf creature token.
        this.addAbility(new FaldornDreadWolfHeraldTriggeredAbility());

        // {1}, {T}, Discard a card: Exile the top card of your library. You may play it this turn.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private FaldornDreadWolfHerald(final FaldornDreadWolfHerald card) {
        super(card);
    }

    @Override
    public FaldornDreadWolfHerald copy() {
        return new FaldornDreadWolfHerald(this);
    }
}

class FaldornDreadWolfHeraldTriggeredAbility extends TriggeredAbilityImpl {

    FaldornDreadWolfHeraldTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()));
    }

    private FaldornDreadWolfHeraldTriggeredAbility(final FaldornDreadWolfHeraldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FaldornDreadWolfHeraldTriggeredAbility copy() {
        return new FaldornDreadWolfHeraldTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                EntersTheBattlefieldEvent eEvent = (EntersTheBattlefieldEvent) event;
                return eEvent.getFromZone() == Zone.EXILED && eEvent.getTarget().isLand(game);
            case SPELL_CAST:
                return Optional
                        .ofNullable(game.getSpell(event.getTargetId()))
                        .map(Spell::getFromZone)
                        .equals(Zone.EXILED);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you cast a spell from exile or a land enters the battlefield under your control from exile, ";
    }
}
