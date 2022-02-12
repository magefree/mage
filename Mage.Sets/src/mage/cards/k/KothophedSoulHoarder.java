
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class KothophedSoulHoarder extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public KothophedSoulHoarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a permanent owned by another player is put into the graveyard from the battlefield, you draw one card and lose 1 life.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("you draw a card");
        Ability ability = new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD, effect, filter,
                "Whenever a permanent owned by another player is put into a graveyard from the battlefield, ", false);
        effect = new LoseLifeSourceControllerEffect(1);
        effect.setText("and lose 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private KothophedSoulHoarder(final KothophedSoulHoarder card) {
        super(card);
    }

    @Override
    public KothophedSoulHoarder copy() {
        return new KothophedSoulHoarder(this);
    }
}

class KothophedSoulHoarderTriggeredAbility extends TriggeredAbilityImpl {

    public KothophedSoulHoarderTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public KothophedSoulHoarderTriggeredAbility(final KothophedSoulHoarderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KothophedSoulHoarderTriggeredAbility copy() {
        return new KothophedSoulHoarderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD && zEvent.getFromZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(zEvent.getTargetId());
            Player controller = game.getPlayer(getControllerId());
            return card != null && controller != null && controller.hasOpponent(card.getOwnerId(), game);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature leaves an opponent's graveyard, " ;
    }
}
