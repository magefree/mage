package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OgreHeadHelm extends CardImpl {

    public OgreHeadHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 2)));

        // Whenever Ogre-Head Helm or equipped creature deals combat damage to a player, you may sacrifice it. If you do, discard your hand, then draw three cards.
        this.addAbility(new OgreHeadHelmTriggeredAbility());

        // Reconfigure {3}
        this.addAbility(new ReconfigureAbility("{3}"));
    }

    private OgreHeadHelm(final OgreHeadHelm card) {
        super(card);
    }

    @Override
    public OgreHeadHelm copy() {
        return new OgreHeadHelm(this);
    }
}

class OgreHeadHelmTriggeredAbility extends TriggeredAbilityImpl {

    OgreHeadHelmTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OgreHeadHelmEffect());
    }

    private OgreHeadHelmTriggeredAbility(final OgreHeadHelmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OgreHeadHelmTriggeredAbility copy() {
        return new OgreHeadHelmTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        if (getSourceId().equals(event.getSourceId())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
            return true;
        }
        Permanent permanent = getSourcePermanentOrLKI(game);
        if (permanent != null && event.getSourceId().equals(permanent.getAttachedTo())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or equipped creature deals combat damage to a player, " +
                "you may sacrifice it. If you do, discard your hand, then draw three cards.";
    }
}

class OgreHeadHelmEffect extends OneShotEffect {

    OgreHeadHelmEffect() {
        super(Outcome.DrawCard);
    }

    private OgreHeadHelmEffect(final OgreHeadHelmEffect effect) {
        super(effect);
    }

    @Override
    public OgreHeadHelmEffect copy() {
        return new OgreHeadHelmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null || !player.chooseUse(
                outcome, "Sacrifice " + permanent.getName() + '?', source, game
        ) || !permanent.sacrifice(source, game)) {
            return false;
        }
        player.discard(player.getHand(), false, source, game);
        player.drawCards(3, source, game);
        return true;
    }
}
