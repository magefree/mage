
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class ThaliaHereticCathar extends CardImpl {

    public ThaliaHereticCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Creatures and nonbasic lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThaliaTapEffect()));
    }

    public ThaliaHereticCathar(final ThaliaHereticCathar card) {
        super(card);
    }

    @Override
    public ThaliaHereticCathar copy() {
        return new ThaliaHereticCathar(this);
    }
}

class ThaliaTapEffect extends ReplacementEffectImpl {

    ThaliaTapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Creatures and nonbasic lands your opponents control enter the battlefield tapped";
    }

    ThaliaTapEffect(final ThaliaTapEffect effect) {
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
            if (permanent != null && (permanent.isCreature() ||
                    (permanent.isLand() && !permanent.isBasic()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ThaliaTapEffect copy() {
        return new ThaliaTapEffect(this);
    }
}
