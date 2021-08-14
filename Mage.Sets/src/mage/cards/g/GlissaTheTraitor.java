

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class GlissaTheTraitor extends CardImpl {
    public GlissaTheTraitor (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // First strike, 
        this.addAbility(FirstStrikeAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Whenever a creature an opponent controls dies, you may return target artifact card from your graveyard to your hand.
        this.addAbility(new GlissaTheTraitorTriggeredAbility());
    }

    public GlissaTheTraitor (final GlissaTheTraitor card) {
        super(card);
    }

    @Override
    public GlissaTheTraitor copy() {
        return new GlissaTheTraitor(this);
    }

}

class GlissaTheTraitorTriggeredAbility extends TriggeredAbilityImpl {
    private static final FilterCard filter = new FilterCard("artifact card from your graveyard");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    GlissaTheTraitorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
        this.addTarget(new TargetCardInYourGraveyard(filter));
    }

    GlissaTheTraitorTriggeredAbility(final GlissaTheTraitorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GlissaTheTraitorTriggeredAbility copy() {
        return new GlissaTheTraitorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent)event).isDiesEvent()) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (p != null && p.isCreature(game) && game.getOpponents(this.getControllerId()).contains(p.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature an opponent controls is put into a graveyard from the battlefield, " ;
    }
}
