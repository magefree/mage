
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class OathOfTeferi extends CardImpl {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("another target permanent you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public OathOfTeferi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);

        // When Oath of Teferi enters the battlefield, exile another target permanent you control. Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new OathOfTeferiBlinkEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // You may activate the loyalty abilities of planeswalkers you control twice each turn rather than only once.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OathOfTeferiLoyaltyEffect()));
    }

    public OathOfTeferi(final OathOfTeferi card) {
        super(card);
    }

    @Override
    public OathOfTeferi copy() {
        return new OathOfTeferi(this);
    }
}

class OathOfTeferiBlinkEffect extends OneShotEffect {

    private static final String effectText = "exile another target permanent you control. Return it to the battlefield under its owner's control at the beginning of the next end step";

    OathOfTeferiBlinkEffect() {
        super(Outcome.Detriment);
        staticText = effectText;
    }

    OathOfTeferiBlinkEffect(OathOfTeferiBlinkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                int zcc = permanent.getZoneChangeCounter(game);
                controller.moveCards(permanent, Zone.EXILED, source, game);
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                effect.setTargetPointer(new FixedTarget(permanent.getId(), zcc + 1));
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public OathOfTeferiBlinkEffect copy() {
        return new OathOfTeferiBlinkEffect(this);
    }

}

class OathOfTeferiLoyaltyEffect extends ContinuousEffectImpl {

    public OathOfTeferiLoyaltyEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may activate the loyalty abilities of planeswalkers you control twice each turn rather than only once";
    }

    public OathOfTeferiLoyaltyEffect(final OathOfTeferiLoyaltyEffect effect) {
        super(effect);
    }

    @Override
    public OathOfTeferiLoyaltyEffect copy() {
        return new OathOfTeferiLoyaltyEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setLoyaltyUsePerTurn(Math.max(2, controller.getLoyaltyUsePerTurn()));
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
