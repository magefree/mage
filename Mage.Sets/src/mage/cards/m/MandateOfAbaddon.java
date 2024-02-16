package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MandateOfAbaddon extends CardImpl {

    public MandateOfAbaddon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Choose target creature you control. Destroy all creatures with power less than that creature's power.
        this.getSpellAbility().addEffect(new MandateOfAbaddonEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private MandateOfAbaddon(final MandateOfAbaddon card) {
        super(card);
    }

    @Override
    public MandateOfAbaddon copy() {
        return new MandateOfAbaddon(this);
    }
}

class MandateOfAbaddonEffect extends OneShotEffect {

    MandateOfAbaddonEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control. " +
                "Destroy all creatures with power less than that creature's power";
    }

    private MandateOfAbaddonEffect(final MandateOfAbaddonEffect effect) {
        super(effect);
    }

    @Override
    public MandateOfAbaddonEffect copy() {
        return new MandateOfAbaddonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        int power = creature.getPower().getValue();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (permanent.getPower().getValue() < power) {
                permanent.destroy(source, game);
            }
        }
        return true;
    }
}
