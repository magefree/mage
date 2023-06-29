package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

public class DragonMenaceAndStealArtifactToken extends TokenImpl {

    public DragonMenaceAndStealArtifactToken() {
        super("Dragon Token", "6/6 black and red Dragon creature token with flying, menace, and \"Whenever this creature deals combat damage to a player, gain control of target artifact that player controls.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
        addAbility(new MenaceAbility(false));

        addAbility(new DragonTokenTriggeredAbility());
    }

    public DragonMenaceAndStealArtifactToken(final DragonMenaceAndStealArtifactToken token) { super(token); }

    public DragonMenaceAndStealArtifactToken copy() { return new DragonMenaceAndStealArtifactToken(this); }

}

class DragonTokenTriggeredAbility extends TriggeredAbilityImpl {

    public DragonTokenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.EndOfGame));
        this.addTarget(new TargetArtifactPermanent());
    }

    public DragonTokenTriggeredAbility(final DragonTokenTriggeredAbility ability) { super(ability); }

    @Override
    public DragonTokenTriggeredAbility copy() { return new DragonTokenTriggeredAbility(this); }

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
