package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public final class CowardKiller extends SplitCard {

    public CowardKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}", "{2}{R}{R}", SpellAbilityType.SPLIT);

        // Coward
        // Target creature can't block this turn and becomes a Coward in addition to its other types until end of turn.
        // Time travel.

        getLeftHalfCard().getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addEffect(new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, SubType.COWARD, false).setText(" and becomes a Coward in addition to its other types until end of turn"));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getLeftHalfCard().getSpellAbility().addEffect(new TimeTravelEffect().concatBy("<br>"));

        // Killer
        // Killer deals 3 damage to target creature and each other creature that shares a creature type with it.
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getRightHalfCard().getSpellAbility().addEffect(new KillerEffect());

    }

    private CowardKiller(final CowardKiller card) {
        super(card);
    }

    @Override
    public CowardKiller copy() {
        return new CowardKiller(this);
    }
}

class KillerEffect extends OneShotEffect {

    KillerEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to target creature and each other creature that shares a creature type with it";
    }

    private KillerEffect(final KillerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null) {
            target.damage(3, source, game);
            for (Permanent p : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                if (!target.getId().equals(p.getId()) && p.shareCreatureTypes(game,target)) {
                    p.damage(3, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public KillerEffect copy() {
        return new KillerEffect(this);
    }
}
