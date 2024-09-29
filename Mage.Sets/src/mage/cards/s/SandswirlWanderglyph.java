package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class SandswirlWanderglyph extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("a spell during their turn");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }
    public SandswirlWanderglyph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
        this.nightCard = true;
        this.color.setWhite(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent casts a spell during their turn, they can't attack you or planeswalkers you control this turn.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD,
                new CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect(), filter, false, SetTargetPointer.PLAYER));

        // Each opponent who attacked you or a planeswalker you control this turn can't cast spells.
        this.addAbility(new SimpleStaticAbility(new SandswirlWanderglyphCantCastEffect()), new PlayersAttackedThisTurnWatcher());
    }

    private SandswirlWanderglyph(final SandswirlWanderglyph card) {
        super(card);
    }

    @Override
    public SandswirlWanderglyph copy() {
        return new SandswirlWanderglyph(this);
    }
}

class SandswirlWanderglyphCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    SandswirlWanderglyphCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each opponent who attacked you or a planeswalker you control this turn can't cast spells";
    }

    private SandswirlWanderglyphCantCastEffect(final SandswirlWanderglyphCantCastEffect effect) {
        super(effect);
    }

    @Override
    public SandswirlWanderglyphCantCastEffect copy() {
        return new SandswirlWanderglyphCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.isActivePlayer(event.getPlayerId()) && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            PlayersAttackedThisTurnWatcher watcher = game.getState().getWatcher(PlayersAttackedThisTurnWatcher.class);
            return watcher != null && watcher.hasPlayerAttackedPlayerOrControlledPlaneswalker(event.getPlayerId(), source.getControllerId());
        }
        return false;
    }

}
class CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect extends RestrictionEffect {
    CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect() {
        super(Duration.EndOfTurn);
        staticText = "they can't attack you or planeswalkers you control this turn";
    }

    private CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect(final CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect copy() {
        return new CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect(this);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (game.getPlayer(defenderId) != null){
            return !(source.getControllerId().equals(defenderId));
        }
        Permanent defender = game.getPermanent(defenderId);
        if (defender != null && defender.isPlaneswalker()){
            return !(source.getControllerId().equals(defender.getControllerId()));
        }
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getControllerId().equals(getTargetPointer().getFirst(game, source));
    }
}
