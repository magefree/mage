
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class KurkeshOnakkeAncient extends CardImpl {

    public KurkeshOnakkeAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay {R}.  If you do, copy that ability.  You may choose new targets for the copy.
        this.addAbility(new KurkeshOnakkeAncientTriggeredAbility());
    }

    public KurkeshOnakkeAncient(final KurkeshOnakkeAncient card) {
        super(card);
    }

    @Override
    public KurkeshOnakkeAncient copy() {
        return new KurkeshOnakkeAncient(this);
    }
}

class KurkeshOnakkeAncientTriggeredAbility extends TriggeredAbilityImpl {

    KurkeshOnakkeAncientTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KurkeshOnakkeAncientEffect(), false);
    }

    KurkeshOnakkeAncientTriggeredAbility(final KurkeshOnakkeAncientTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KurkeshOnakkeAncientTriggeredAbility copy() {
        return new KurkeshOnakkeAncientTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (source != null && source.isArtifact()) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                    Effect effect = this.getEffects().get(0);
                    effect.setValue("stackAbility", stackAbility);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability of an artifact, if it isn't a mana ability" + super.getRule();
    }
}

class KurkeshOnakkeAncientEffect extends OneShotEffect {

    KurkeshOnakkeAncientEffect() {
        super(Outcome.Benefit);
        this.staticText = ", you may pay {R}. If you do, copy that ability. You may choose new targets for the copy";
    }

    KurkeshOnakkeAncientEffect(final KurkeshOnakkeAncientEffect effect) {
        super(effect);
    }

    @Override
    public KurkeshOnakkeAncientEffect copy() {
        return new KurkeshOnakkeAncientEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ColoredManaCost cost = new ColoredManaCost(ColoredManaSymbol.R);
        if (player != null) {
            if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + "? If you do, copy that ability.  You may choose new targets for the copy.", source, game)) {
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                    StackAbility ability = (StackAbility) getValue("stackAbility");
                    Player controller = game.getPlayer(source.getControllerId());
                    Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                    if (ability != null && controller != null) {
                        ability.createCopyOnStack(game, source, source.getControllerId(), true);
                        game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(": ").append(controller.getLogName()).append(" copied activated ability").toString());
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
