
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class EnigmaSphinx extends CardImpl {

    public EnigmaSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{U}{B}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Enigma Sphinx is put into your graveyard from the battlefield, put it into your library third from the top.
        this.addAbility(new EnigmaSphinxTriggeredAbility(new EnigmaSphinxEffect()));

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private EnigmaSphinx(final EnigmaSphinx card) {
        super(card);
    }

    @Override
    public EnigmaSphinx copy() {
        return new EnigmaSphinx(this);
    }
}

class EnigmaSphinxTriggeredAbility extends TriggeredAbilityImpl {

    public EnigmaSphinxTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EnigmaSphinxTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional);
        setTriggerPhrase("When {this} is put into your graveyard from the battlefield, ");
    }

    EnigmaSphinxTriggeredAbility(EnigmaSphinxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EnigmaSphinxTriggeredAbility copy() {
        return new EnigmaSphinxTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent != null && zEvent.isDiesEvent()
                && permanent.getId().equals(this.getSourceId())
                && // 5/1/2009 If you control an Enigma Sphinx that's owned by another player, it's put into that player's
                //          graveyard from the battlefield, so Enigma Sphinx's middle ability won't trigger.
                permanent.isOwnedBy(permanent.getControllerId())) {
            return true;
        }
        return false;
    }
}

class EnigmaSphinxEffect extends OneShotEffect {

    public EnigmaSphinxEffect() {
        super(Outcome.ReturnToHand);
        staticText = "put it into your library third from the top";
    }

    public EnigmaSphinxEffect(final EnigmaSphinxEffect effect) {
        super(effect);
    }

    @Override
    public EnigmaSphinxEffect copy() {
        return new EnigmaSphinxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = (Card) source.getSourceObjectIfItStillExists(game);
        if (card != null) {
            controller.putCardOnTopXOfLibrary(card, game, source, 3, true);
        }
        return true;
    }
}
