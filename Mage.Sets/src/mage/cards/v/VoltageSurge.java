package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoltageSurge extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("you may sacrifice an artifact");

    public VoltageSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // As an additional cost to cast this spell, you may sacrifice an artifact.
        this.getSpellAbility().addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(0, 1, filter, true)
        ));

        // Voltage Surge deals 2 damage to target creature or planeswalker. If this spell's additional cost was paid, Voltage Surge deals 4 damage instead.
        this.getSpellAbility().addEffect(new VoltageSurgeEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private VoltageSurge(final VoltageSurge card) {
        super(card);
    }

    @Override
    public VoltageSurge copy() {
        return new VoltageSurge(this);
    }
}

class VoltageSurgeEffect extends OneShotEffect {

    VoltageSurgeEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 2 damage to target creature or planeswalker. " +
                "If this spell's additional cost was paid, {this} deals 4 damage instead";
    }

    private VoltageSurgeEffect(final VoltageSurgeEffect effect) {
        super(effect);
    }

    @Override
    public VoltageSurgeEffect copy() {
        return new VoltageSurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        boolean wasPaid = CardUtil.castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .findFirst()
                .isPresent();
        return permanent.damage(wasPaid ? 4 : 2, source, game) > 0;
    }
}
