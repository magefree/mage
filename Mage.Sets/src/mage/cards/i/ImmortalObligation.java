package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author brahle
 */
public final class ImmortalObligation extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from an opponent's graveyard");

    public ImmortalObligation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Return target creature card from an opponent's graveyard to the battlefield under their control with a duty counter on it.
        // For as long as that creature has a duty counter on it, it is goaded, can't attack you or a permanent you control, and can't block creatures you control.
        this.getSpellAbility().addEffect(new ImmortalObligationEffect());
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));
    }

    private ImmortalObligation(final ImmortalObligation card) {
        super(card);
    }

    @Override
    public ImmortalObligation copy() {
        return new ImmortalObligation(this);
    }
}

class ImmortalObligationEffect extends OneShotEffect {

    ImmortalObligationEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return target creature card from an opponent's graveyard to the battlefield under their control with a duty counter on it. " +
                "For as long as that creature has a duty counter on it, it is goaded, can't attack you or a permanent you control, and can't block creatures you control";
    }

    private ImmortalObligationEffect(final ImmortalObligationEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalObligationEffect copy() {
        return new ImmortalObligationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }

        game.setEnterWithCounters(card.getId(), new Counters(CounterType.DUTY.createInstance()));
        if (!controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null)) {
            return false;
        }

        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent != null) {
            ContinuousEffect goadEffect = new ImmortalObligationGoadEffect();
            goadEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(goadEffect, source);

            ContinuousEffect restrictEffect = new ImmortalObligationRestrictionEffect();
            restrictEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(restrictEffect, source);
        }
        return true;
    }
}

class ImmortalObligationGoadEffect extends ContinuousEffectImpl {

    ImmortalObligationGoadEffect() {
        super(Duration.Custom, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "it is goaded";
    }

    private ImmortalObligationGoadEffect(final ImmortalObligationGoadEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalObligationGoadEffect copy() {
        return new ImmortalObligationGoadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.getCounters(game).getCount(CounterType.DUTY) < 1) {
            discard();
            return false;
        }
        permanent.addGoadingPlayer(source.getControllerId());
        return true;
    }
}

class ImmortalObligationRestrictionEffect extends RestrictionEffect {

    ImmortalObligationRestrictionEffect() {
        super(Duration.Custom);
        staticText = "can't attack you or a permanent you control, and can't block creatures you control";
    }

    private ImmortalObligationRestrictionEffect(final ImmortalObligationRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalObligationRestrictionEffect copy() {
        return new ImmortalObligationRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent p = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (p == null || p.getCounters(game).getCount(CounterType.DUTY) < 1) {
            discard();
            return false;
        }
        return permanent.getId().equals(p.getId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        if (source.isControlledBy(defenderId)) {
            return false;
        }
        Permanent permanent = game.getPermanent(defenderId);
        return permanent == null || !permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return !attacker.isControlledBy(source.getControllerId());
    }
}
