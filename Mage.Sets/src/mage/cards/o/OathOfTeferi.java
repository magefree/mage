package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OathOfTeferi extends CardImpl {

    public OathOfTeferi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Teferi enters the battlefield, exile another target permanent you control. Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_TARGET_PERMANENT));
        this.addAbility(ability);

        // You may activate the loyalty abilities of planeswalkers you control twice each turn rather than only once.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OathOfTeferiLoyaltyEffect()));
    }

    private OathOfTeferi(final OathOfTeferi card) {
        super(card);
    }

    @Override
    public OathOfTeferi copy() {
        return new OathOfTeferi(this);
    }
}

class OathOfTeferiLoyaltyEffect extends ContinuousEffectImpl {

    public OathOfTeferiLoyaltyEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may activate the loyalty abilities of planeswalkers you control twice each turn rather than only once";
    }

    private OathOfTeferiLoyaltyEffect(final OathOfTeferiLoyaltyEffect effect) {
        super(effect);
    }

    @Override
    public OathOfTeferiLoyaltyEffect copy() {
        return new OathOfTeferiLoyaltyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
                source.getControllerId(), source, game
        )) {
            permanent.setLoyaltyActivationsAvailable(2);
        }
        return true;
    }
}
