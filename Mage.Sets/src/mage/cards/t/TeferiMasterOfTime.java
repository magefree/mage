package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferiMasterOfTime extends CardImpl {

    public TeferiMasterOfTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.setStartingLoyalty(3);

        // You may activate loyalty abilities of Teferi, Master of Time on any player's turn any time you could cast an instant.
        this.addAbility(new SimpleStaticAbility(new TeferiMasterOfTimeActivationEffect()));

        // +1: Draw a card, then discard a card.
        this.addAbility(new LoyaltyAbility(new DrawDiscardControllerEffect(1, 1), 1));

        // −3: Target creature you don't control phases out.
        Ability ability = new LoyaltyAbility(new PhaseOutTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // −10: Take two extra turns after this one.
        this.addAbility(new LoyaltyAbility(new TeferiMasterOfTimeTurnEffect(), -10));
    }

    private TeferiMasterOfTime(final TeferiMasterOfTime card) {
        super(card);
    }

    @Override
    public TeferiMasterOfTime copy() {
        return new TeferiMasterOfTime(this);
    }
}

class TeferiMasterOfTimeActivationEffect extends AsThoughEffectImpl {

    TeferiMasterOfTimeActivationEffect() {
        super(AsThoughEffectType.ACTIVATE_AS_INSTANT, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may activate loyalty abilities of {this} " +
                "on any player's turn any time you could cast an instant";
    }

    private TeferiMasterOfTimeActivationEffect(final TeferiMasterOfTimeActivationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TeferiMasterOfTimeActivationEffect copy() {
        return new TeferiMasterOfTimeActivationEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return affectedAbility.isControlledBy(source.getControllerId())
                && affectedAbility.getSourceId().equals(source.getSourceId())
                && affectedAbility instanceof LoyaltyAbility;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}

class TeferiMasterOfTimeTurnEffect extends OneShotEffect {

    TeferiMasterOfTimeTurnEffect() {
        super(Outcome.ExtraTurn);
        staticText = "take two extra turns after this one";
    }

    private TeferiMasterOfTimeTurnEffect(final TeferiMasterOfTimeTurnEffect effect) {
        super(effect);
    }

    @Override
    public TeferiMasterOfTimeTurnEffect copy() {
        return new TeferiMasterOfTimeTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
        return true;
    }
}
