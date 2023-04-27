
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Skullsnatcher extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("unblocked attacker you control");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    public Skullsnatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ninjutsu {B} ({B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{B}"));
        
        // Whenever Skullsnatcher deals combat damage to a player, exile up to two target cards from that player's graveyard.
        Effect effect = new ExileTargetEffect(null, "", Zone.GRAVEYARD);
        effect.setText("exile up to two target cards from that player's graveyard");
        this.addAbility(new SkullsnatcherTriggeredAbility(effect));
    }

    private Skullsnatcher(final Skullsnatcher card) {
        super(card);
    }

    @Override
    public Skullsnatcher copy() {
        return new Skullsnatcher(this);
    }
}

class SkullsnatcherTriggeredAbility extends TriggeredAbilityImpl {

    SkullsnatcherTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever {this} deals combat damage to a player, ");
    }

    SkullsnatcherTriggeredAbility(final SkullsnatcherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkullsnatcherTriggeredAbility copy() {
        return new SkullsnatcherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()
                && event.getSourceId().equals(sourceId)) {

            FilterCard filter = new FilterCard("up to two target cards from that player's graveyard");
            filter.add(new OwnerIdPredicate(event.getPlayerId()));
            filter.setMessage("up to two cards in " + game.getPlayer(event.getTargetId()).getLogName() + "'s graveyard");
            this.getTargets().clear();
            this.addTarget(new TargetCardInOpponentsGraveyard(0,2,filter));
            return true;
        }
        return false;
    }
}
