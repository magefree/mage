package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DroneToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.DamagedPlayerThisCombatWatcher;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class KaitoDancingShadow extends CardImpl {

    public KaitoDancingShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAITO);
        this.setStartingLoyalty(3);

        // Whenever one or more creatures you control deal combat damage to a player, you may return one of them to its owner's hand. If you do, you may activate loyalty abilities of Kaito twice this turn rather than only once.
        Ability ability = new DealCombatDamageControlledTriggeredAbility(Zone.BATTLEFIELD, new KaitoDancingShadowEffect(), true);
        ability.addWatcher(new DamagedPlayerThisCombatWatcher());
        this.addAbility(ability);

        // +1: Up to one target creature can't attack or block until your next turn.
        Ability KaitoCantAttackOrBlockAbility = new LoyaltyAbility(new CantAttackTargetEffect(Duration.UntilYourNextTurn).setText("Up to one target creature can't attack"), 1);
        KaitoCantAttackOrBlockAbility.addEffect(new CantBlockTargetEffect(Duration.UntilYourNextTurn).setText("or block until your next turn"));
        KaitoCantAttackOrBlockAbility.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(KaitoCantAttackOrBlockAbility);

        // 0: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 0));

        // -2: Create a 2/2 colorless Drone artifact creature token with deathtouch and "When this creature leaves the battlefield, each opponent loses 2 life and you gain 2 life."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DroneToken()), -2));
    }

    private KaitoDancingShadow(final KaitoDancingShadow card) {
        super(card);
    }

    @Override
    public KaitoDancingShadow copy() {
        return new KaitoDancingShadow(this);
    }
}

class KaitoDancingShadowEffect extends OneShotEffect {

    KaitoDancingShadowEffect() {
        super(Outcome.Benefit);
        this.setText("you may return one of them to its owner's hand. If you do, you may activate loyalty abilities of Kaito twice this turn rather than only once");
    }

    private KaitoDancingShadowEffect(final KaitoDancingShadowEffect effect) {
        super(effect);
    }

    @Override
    public KaitoDancingShadowEffect copy() {
        return new KaitoDancingShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DamagedPlayerThisCombatWatcher watcher = game.getState().getWatcher(DamagedPlayerThisCombatWatcher.class);
        if (watcher == null) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || damagedPlayer == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent();
        filter.add(new PermanentReferenceInCollectionPredicate(
                watcher.getPermanents(controller.getId(),damagedPlayer.getId())));
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        target.setTargetName("creature to return to hand?");
        if (target.chooseTarget(Outcome.ReturnToHand, source.getControllerId(), source, game)) {
            Card card = game.getPermanent(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);

                ContinuousEffectImpl effect = new KaitoDancingShadowIncreaseLoyaltyUseEffect();
                game.addEffect(effect, source);
            }
        }

        return true;
    }
}

class KaitoDancingShadowIncreaseLoyaltyUseEffect extends ContinuousEffectImpl {

    public KaitoDancingShadowIncreaseLoyaltyUseEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
    }

    public KaitoDancingShadowIncreaseLoyaltyUseEffect(final KaitoDancingShadowIncreaseLoyaltyUseEffect effect) {
        super(effect);
    }

    @Override
    public KaitoDancingShadowIncreaseLoyaltyUseEffect copy() {
        return new KaitoDancingShadowIncreaseLoyaltyUseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent kaito = source.getSourcePermanentIfItStillExists(game);
        if (kaito == null) {
            discard();
            return false;
        }
        kaito.setLoyaltyActivationsAvailable(2);
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

