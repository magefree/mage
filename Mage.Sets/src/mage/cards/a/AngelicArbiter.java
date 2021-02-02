package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.watchers.common.CastSpellLastTurnWatcher;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class AngelicArbiter extends CardImpl {

    public AngelicArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each opponent who cast a spell this turn can't attack with creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelicArbiterCantAttackTargetEffect(Duration.WhileOnBattlefield)));

        // Each opponent who attacked with a creature this turn can't cast spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelicArbiterEffect2()), new PlayerAttackedWatcher());
    }

    private AngelicArbiter(final AngelicArbiter card) {
        super(card);
    }

    @Override
    public AngelicArbiter copy() {
        return new AngelicArbiter(this);
    }

}

class AngelicArbiterCantAttackTargetEffect extends RestrictionEffect {

    public AngelicArbiterCantAttackTargetEffect(Duration duration) {
        super(duration);
        staticText = "Each opponent who cast a spell this turn can't attack with creatures";
    }

    public AngelicArbiterCantAttackTargetEffect(final AngelicArbiterCantAttackTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.isActivePlayer(permanent.getControllerId()) && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(permanent.getControllerId()) > 0;
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public AngelicArbiterCantAttackTargetEffect copy() {
        return new AngelicArbiterCantAttackTargetEffect(this);
    }
}

class AngelicArbiterEffect2 extends ContinuousRuleModifyingEffectImpl {

    public AngelicArbiterEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each opponent who attacked with a creature this turn can't cast spells";
    }

    public AngelicArbiterEffect2(final AngelicArbiterEffect2 effect) {
        super(effect);
    }

    @Override
    public AngelicArbiterEffect2 copy() {
        return new AngelicArbiterEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.isActivePlayer(event.getPlayerId()) && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            PlayerAttackedWatcher watcher = game.getState().getWatcher(PlayerAttackedWatcher.class);
            return watcher != null && watcher.getNumberOfAttackersCurrentTurn(event.getPlayerId()) > 0;
        }
        return false;
    }

}
