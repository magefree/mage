
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class Manglehorn extends CardImpl {

    public Manglehorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Manglehorn enters the battlefield, you may destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // Artifacts your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ManglehornTapEffect()));
    }

    public Manglehorn(final Manglehorn card) {
        super(card);
    }

    @Override
    public Manglehorn copy() {
        return new Manglehorn(this);
    }
}

class ManglehornTapEffect extends ReplacementEffectImpl {

    ManglehornTapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Artifacts your opponents control enter the battlefield tapped";
    }

    ManglehornTapEffect(final ManglehornTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isArtifact()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ManglehornTapEffect copy() {
        return new ManglehornTapEffect(this);
    }
}
