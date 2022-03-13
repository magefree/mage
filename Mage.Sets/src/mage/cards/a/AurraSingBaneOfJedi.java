package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.effects.common.SetPlayerLifeAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.AurraSingBaneOfJediEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class AurraSingBaneOfJedi extends CardImpl {

    public AurraSingBaneOfJedi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AURRA);

        this.setStartingLoyalty(3);

        // +1: You may have {this} deal 2 damage to target creature. If you don't, {this} deals 1 damage to you.
        Ability ability = new LoyaltyAbility(new AurraSingBaneOfJediEffect(), +1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -4: Target player gets an emblem wiht "Whenever a nontoken creature you control leave the battlefied, discard a card.".
        ability = new LoyaltyAbility(new GetEmblemTargetPlayerEffect(new AurraSingBaneOfJediEmblem()), -4);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -6: Each player discards their hand and sacrificies all creatures they control. Each player's life total becomes 1."
        ability = new LoyaltyAbility(new DiscardHandAllEffect(), -6);
        ability.addEffect(new SacrificeAllEffect());
        Effect effect = new SetPlayerLifeAllEffect(1, TargetController.ANY);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AurraSingBaneOfJedi(final AurraSingBaneOfJedi card) {
        super(card);
    }

    @Override
    public AurraSingBaneOfJedi copy() {
        return new AurraSingBaneOfJedi(this);
    }
}

class AurraSingBaneOfJediEffect extends OneShotEffect {

    public AurraSingBaneOfJediEffect() {
        super(Outcome.Damage);
        staticText = "You may have {this} deal 2 damage to target creature. If you don't, {this} deals 1 damage to you";
    }

    public AurraSingBaneOfJediEffect(final AurraSingBaneOfJediEffect effect) {
        super(effect);
    }

    @Override
    public AurraSingBaneOfJediEffect copy() {
        return new AurraSingBaneOfJediEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(outcome, "Deal 2 damage to " + game.getPermanent(getTargetPointer().getFirst(game, source)).getName() + '?', source, game)) {
                new DamageTargetEffect(2).apply(game, source);
            } else {
                new DamageControllerEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class SacrificeAllEffect extends OneShotEffect {

    SacrificeAllEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "and sacrificies all creatures they control";
    }

    SacrificeAllEffect(final SacrificeAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game);
        for (Permanent p : permanents) {
            p.sacrifice(source, game);
        }
        return true;
    }

    @Override
    public SacrificeAllEffect copy() {
        return new SacrificeAllEffect(this);
    }
}
