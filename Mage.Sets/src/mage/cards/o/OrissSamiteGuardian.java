
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class OrissSamiteGuardian extends CardImpl {

    public OrissSamiteGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Prevent all damage that would be dealt to target creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Grandeur - Discard another card named Oriss, Samite Guardian: Target player can't cast spells this turn, and creatures that player controls can't attack this turn.
        ability = new GrandeurAbility(new OrissSamiteGuardianEffect(), "Oriss, Samite Guardian");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private OrissSamiteGuardian(final OrissSamiteGuardian card) {
        super(card);
    }

    @Override
    public OrissSamiteGuardian copy() {
        return new OrissSamiteGuardian(this);
    }
}

class OrissSamiteGuardianEffect extends OneShotEffect {

    public OrissSamiteGuardianEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player can't cast spells this turn, and creatures that player controls can't attack this turn";
    }

    public OrissSamiteGuardianEffect(final OrissSamiteGuardianEffect effect) {
        super(effect);
    }

    @Override
    public OrissSamiteGuardianEffect copy() {
        return new OrissSamiteGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.addEffect(new OrissSamiteGuardianCantCastEffect(), source);
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures that player controls");
            filter.add(new ControllerIdPredicate(getTargetPointer().getFirst(game, source)));
            ContinuousEffect effect = new CantAttackAnyPlayerAllEffect(Duration.EndOfTurn, filter);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class OrissSamiteGuardianCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    OrissSamiteGuardianCantCastEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't cast spells this turn";
    }

    OrissSamiteGuardianCantCastEffect(final OrissSamiteGuardianCantCastEffect effect) {
        super(effect);
    }

    @Override
    public OrissSamiteGuardianCantCastEffect copy() {
        return new OrissSamiteGuardianCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null && player.getId().equals(event.getPlayerId());
    }
}
