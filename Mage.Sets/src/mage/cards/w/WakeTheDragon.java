package mage.cards.w;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;

import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 * @author rullinoiz
 */
public final class WakeTheDragon extends CardImpl {

    public WakeTheDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{R}");
        

        // Create a 6/6 black and red Dragon creature token with flying, menace, and "Whenever this creature deals combat damage to a player, gain control of target artifact that player controls."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WakeTheDragonToken()));

        // Flashback {6}{B}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{B}{R}")));
    }

    private WakeTheDragon(final WakeTheDragon card) {
        super(card);
    }

    @Override
    public WakeTheDragon copy() {
        return new WakeTheDragon(this);
    }
}

class WakeTheDragonToken extends TokenImpl {

    public WakeTheDragonToken() {
        super("Dragon Token", "6/6 black and red Dragon creature token with flying, menace, and \"Whenever this creature deals combat damage to a player, gain control of target artifact that player controls.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
        addAbility(new MenaceAbility(false));

        addAbility(new WakeTheDragonTokenTriggeredAbility());
    }

    public WakeTheDragonToken(final WakeTheDragonToken token) { super(token); }

    public WakeTheDragonToken copy() { return new WakeTheDragonToken(this); }

}

class WakeTheDragonTokenTriggeredAbility extends TriggeredAbilityImpl {

    public WakeTheDragonTokenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.EndOfGame));
        this.addTarget(new TargetArtifactPermanent());
    }

    public WakeTheDragonTokenTriggeredAbility(final WakeTheDragonTokenTriggeredAbility ability) { super(ability); }

    @Override
    public WakeTheDragonTokenTriggeredAbility copy() { return new WakeTheDragonTokenTriggeredAbility(this); }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact " + opponent.getLogName() + " controls");
                filter.add(new ControllerIdPredicate(opponent.getId()));

                this.getTargets().clear();
                this.addTarget(new TargetArtifactPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals combat damage to a player, gain control of target artifact that player controls.";
    }

}