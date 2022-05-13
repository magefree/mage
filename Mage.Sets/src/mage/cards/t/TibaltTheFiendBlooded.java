
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.CardsInTargetHandCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public final class TibaltTheFiendBlooded extends CardImpl {

    public TibaltTheFiendBlooded(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIBALT);

        this.setStartingLoyalty(2);

        // +1: Draw a card, then discard a card at random.
        LoyaltyAbility ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        Effect effect = new DiscardControllerEffect(1, true);
        effect.setText(", then discard a card at random");
        ability.addEffect(effect);
        this.addAbility(ability);
        // -4: Tibalt, the Fiend-Blooded deals damage equal to the number of cards in target player's hand to that player.
        effect = new DamageTargetEffect(CardsInTargetHandCount.instance, true);
        effect.setText("{this} deals damage equal to the number of cards in target player's hand to that player");
        ability = new LoyaltyAbility(effect, -4);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // -6: Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn.
        this.addAbility(new LoyaltyAbility(new TibaltTheFiendBloodedThirdEffect(), -6));
    }

    private TibaltTheFiendBlooded(final TibaltTheFiendBlooded card) {
        super(card);
    }

    @Override
    public TibaltTheFiendBlooded copy() {
        return new TibaltTheFiendBlooded(this);
    }
}

class TibaltTheFiendBloodedFirstEffect extends OneShotEffect {

    public TibaltTheFiendBloodedFirstEffect() {
        super(Outcome.Benefit);
        this.staticText = "Draw a card, then discard a card at random";
    }

    public TibaltTheFiendBloodedFirstEffect(final TibaltTheFiendBloodedFirstEffect effect) {
        super(effect);
    }

    @Override
    public TibaltTheFiendBloodedFirstEffect copy() {
        return new TibaltTheFiendBloodedFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
            Card card = player.getHand().getRandom(game);
            player.discard(card, false, source, game);
            return true;
        }
        return false;
    }
}

class TibaltTheFiendBloodedThirdEffect extends OneShotEffect {

    public TibaltTheFiendBloodedThirdEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn";
    }

    public TibaltTheFiendBloodedThirdEffect(final TibaltTheFiendBloodedThirdEffect effect) {
        super(effect);
    }

    @Override
    public TibaltTheFiendBloodedThirdEffect copy() {
        return new TibaltTheFiendBloodedThirdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            permanent.untap(game);

            ContinuousEffect effect = new TibaltTheFiendBloodedControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);

            effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class TibaltTheFiendBloodedControlEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public TibaltTheFiendBloodedControlEffect(UUID controllerId) {
        super(Duration.EndOfTurn, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public TibaltTheFiendBloodedControlEffect(final TibaltTheFiendBloodedControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public TibaltTheFiendBloodedControlEffect copy() {
        return new TibaltTheFiendBloodedControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game, source);
        }
        return false;
    }
}
