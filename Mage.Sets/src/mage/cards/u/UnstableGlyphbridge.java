package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class UnstableGlyphbridge extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("a spell during their turn");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public UnstableGlyphbridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}{W}{W}",
                "Sandswirl Wanderglyph",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GOLEM}, "W"
        );

        // Unstable Glyphbridge
        // When Unstable Glyphbridge enters the battlefield, if you cast it, for each player, choose a creature with power 2 or less that player controls. Then destroy all creatures except creatures chosen this way.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new UnstableGlyphbridgeEffect())
                .withInterveningIf(CastFromEverywhereSourceCondition.instance));

        // Craft with artifact {3}{W}{W}
        this.getLeftHalfCard().addAbility(new CraftAbility("{3}{W}{W}"));

        // Sandswirl Wanderglyph
        this.getRightHalfCard().setPT(5, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever an opponent casts a spell during their turn, they can't attack you or planeswalkers you control this turn.
        this.getRightHalfCard().addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD,
                new CantAttackSourcePlayerOrPlaneswalkerThisTurnEffect(), filter, false, SetTargetPointer.PLAYER));

        // Each opponent who attacked you or a planeswalker you control this turn can't cast spells.
        Ability ability = new SimpleStaticAbility(new SandswirlWanderglyphCantCastEffect());
        ability.addWatcher(new PlayersAttackedThisTurnWatcher());
        this.getRightHalfCard().addAbility(ability);
    }

    private UnstableGlyphbridge(final UnstableGlyphbridge card) {
        super(card);
    }

    @Override
    public UnstableGlyphbridge copy() {
        return new UnstableGlyphbridge(this);
    }
}


class UnstableGlyphbridgeEffect extends OneShotEffect {
    UnstableGlyphbridgeEffect() {
        super(Outcome.Benefit);
        staticText = "for each player, choose a creature with power 2 or less that player controls. " +
                "Then destroy all creatures except creatures chosen this way.";
    }

    private UnstableGlyphbridgeEffect(final UnstableGlyphbridgeEffect effect) {
        super(effect);
    }

    @Override
    public UnstableGlyphbridgeEffect copy() {
        return new UnstableGlyphbridgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 2 or less");
            filter.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
            filter.add(new ControllerIdPredicate(playerId));
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            target.withChooseHint(player.getName() + " controls");

            controller.choose(Outcome.PutCreatureInPlay, target, source, game);
            cards.add(target.getFirstTarget());
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), source, game)) {
            if (!cards.contains(permanent.getId())) {
                permanent.destroy(source, game);
            }
        }
        return true;
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
        if (game.getPlayer(defenderId) != null) {
            return !(source.getControllerId().equals(defenderId));
        }
        Permanent defender = game.getPermanent(defenderId);
        if (defender != null && defender.isPlaneswalker()) {
            return !(source.getControllerId().equals(defender.getControllerId()));
        }
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getControllerId().equals(getTargetPointer().getFirst(game, source));
    }
}
