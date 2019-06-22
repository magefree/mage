package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraFlamesFury extends CardImpl {

    public ChandraFlamesFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Chandra, Flame's Fury deals 2 damage to any target.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(2), 1);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // −2: Chandra, Flame's Fury deals 4 damage to target creature and 2 damage to that creature's controller.
        ability = new LoyaltyAbility(new ChandraFlamesFuryEffect(), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −8: Chandra, Flame's Fury deals 10 damage to target player and each creature that player controls.
        ability = new LoyaltyAbility(new DamageTargetEffect(10), -8);
        ability.addEffect(new DamageAllControlledTargetEffect(
                10, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and each creature that player controls"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ChandraFlamesFury(final ChandraFlamesFury card) {
        super(card);
    }

    @Override
    public ChandraFlamesFury copy() {
        return new ChandraFlamesFury(this);
    }
}

class ChandraFlamesFuryEffect extends OneShotEffect {

    ChandraFlamesFuryEffect() {
        super(Outcome.Benefit);
        staticText = "deals 4 damage to target creature and 2 damage to that creature's controller.";
    }

    private ChandraFlamesFuryEffect(final ChandraFlamesFuryEffect effect) {
        super(effect);
    }

    @Override
    public ChandraFlamesFuryEffect copy() {
        return new ChandraFlamesFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        permanent.damage(4, source.getSourceId(), game);
        player.damage(2, source.getSourceId(), game);
        return true;
    }
}