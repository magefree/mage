package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RequisitionRaid extends CardImpl {

    public RequisitionRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {1} -- Destroy target enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new TargetEnchantmentPermanent())
                .withCost(new GenericManaCost(1)));

        // + {1} -- Put a +1/+1 counter on each creature target player controls.
        this.getSpellAbility().addMode(new Mode(new RequisitionRaidEffect())
                .addTarget(new TargetPlayer())
                .withCost(new GenericManaCost(1)));
    }

    private RequisitionRaid(final RequisitionRaid card) {
        super(card);
    }

    @Override
    public RequisitionRaid copy() {
        return new RequisitionRaid(this);
    }
}

class RequisitionRaidEffect extends OneShotEffect {

    RequisitionRaidEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each creature target player controls";
    }

    private RequisitionRaidEffect(final RequisitionRaidEffect effect) {
        super(effect);
    }

    @Override
    public RequisitionRaidEffect copy() {
        return new RequisitionRaidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                getTargetPointer().getFirst(game, source), source, game
        )) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return true;
    }
}
