package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VancesBlastingCannons extends TransformingDoubleFacedCard {

    public VancesBlastingCannons(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{3}{R}",
                "Spitfire Bastion",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // At the beginning of your upkeep, exile the top card of your library.  If it's a nonland card, you may cast that card this turn.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new VancesBlastingCannonsExileEffect(), TargetController.YOU, false
        ));

        // Whenever you cast your third spell in a turn, transform Vance's Blasting Cannons.
        this.getLeftHalfCard().addAbility(new VancesBlastingCannonsFlipTrigger());

        // Spitfire Bastion
        // (Transforms from Vance's Blasting Cannons.)
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Vance's Blasting Cannons.)</i>")));

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());

        // {2}{R}, {T}: Spitfire Bastion deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(ability);
    }

    private VancesBlastingCannons(final VancesBlastingCannons card) {
        super(card);
    }

    @Override
    public VancesBlastingCannons copy() {
        return new VancesBlastingCannons(this);
    }
}

class VancesBlastingCannonsExileEffect extends OneShotEffect {

    public VancesBlastingCannonsExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. If it's a nonland card, you may cast that card this turn";
    }

    public VancesBlastingCannonsExileEffect(final VancesBlastingCannonsExileEffect effect) {
        super(effect);
    }

    @Override
    public VancesBlastingCannonsExileEffect copy() {
        return new VancesBlastingCannonsExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + (card.isLand(game) ? "" : " <this card may be cast the turn it was exiled");
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand(game)) {
                    ContinuousEffect effect = new CastFromNonHandZoneTargetEffect(Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class CastFromNonHandZoneTargetEffect extends AsThoughEffectImpl {

    public CastFromNonHandZoneTargetEffect(Duration duration) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        staticText = "If it's a nonland card, you may cast that card this turn";
    }

    public CastFromNonHandZoneTargetEffect(final CastFromNonHandZoneTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastFromNonHandZoneTargetEffect copy() {
        return new CastFromNonHandZoneTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (getTargetPointer().getTargets(game, source).contains(objectId)
                && source.isControlledBy(affectedControllerId)) {
            Card card = game.getCard(objectId);
            if (card != null) {
                return true;
            }
        }
        return false;
    }
}

class VancesBlastingCannonsFlipTrigger extends TriggeredAbilityImpl {

    public VancesBlastingCannonsFlipTrigger() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(), true);
    }

    public VancesBlastingCannonsFlipTrigger(final VancesBlastingCannonsFlipTrigger ability) {
        super(ability);
    }

    @Override
    public VancesBlastingCannonsFlipTrigger copy() {
        return new VancesBlastingCannonsFlipTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your third spell in a turn, you may transform {this}.";
    }
}
