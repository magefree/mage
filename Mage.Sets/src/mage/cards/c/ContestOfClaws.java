package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2;

/**
 * @author jimga150
 */
public final class ContestOfClaws extends CardImpl {

    public ContestOfClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control deals damage equal to its power to another target creature. If excess damage was dealt this way, discover X, where X is that excess damage.
        this.getSpellAbility().addEffect(new ContestOfClawsDamageEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
    }

    private ContestOfClaws(final ContestOfClaws card) {
        super(card);
    }

    @Override
    public ContestOfClaws copy() {
        return new ContestOfClaws(this);
    }
}

// Based on Fall of the Hammer and Lacerate Flesh
class ContestOfClawsDamageEffect extends OneShotEffect {

    ContestOfClawsDamageEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target creature you control deals damage equal to its power to another target creature. " +
                "If excess damage was dealt this way, discover X, where X is that excess damage.";
    }

    private ContestOfClawsDamageEffect(final ContestOfClawsDamageEffect effect) {
        super(effect);
    }

    @Override
    public ContestOfClawsDamageEffect copy() {
        return new ContestOfClawsDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ownCreature = game.getPermanent(source.getFirstTarget());
        Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (ownCreature == null || targetCreature == null) {
            return false;
        }
        int excess = targetCreature.damageWithExcess( ownCreature.getPower().getValue(), ownCreature.getId(), source, game);
        if (excess > 0) {
            Optional.ofNullable(source)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> DiscoverEffect.doDiscover(player, excess, game, source));
        }
        return true;
    }
}
