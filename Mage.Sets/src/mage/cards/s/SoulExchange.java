package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author MarcoMarin
 */
public final class SoulExchange extends CardImpl {

    public SoulExchange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // As an additional cost to cast Soul Exchange, exile a creature you control.
        Cost cost = new ExileTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_A_CREATURE));
        this.getSpellAbility().addCost(cost);
        // Return target creature card from your graveyard to the battlefield. Put a +2/+2 counter on that creature if the exiled creature was a Thrull.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new SoulExchangeEffect());

    }

    private SoulExchange(final SoulExchange card) {
        super(card);
    }

    @Override
    public SoulExchange copy() {
        return new SoulExchange(this);
    }
}

class SoulExchangeEffect extends OneShotEffect {

    public SoulExchangeEffect() {
        super(Outcome.Benefit);
        this.setText("Return target creature card from your graveyard to the battlefield. Put a +2/+2 counter on that creature if the exiled creature was a Thrull.");
    }

    public SoulExchangeEffect(final SoulExchangeEffect effect) {
        super(effect);
    }

    @Override
    public SoulExchangeEffect copy() {
        return new SoulExchangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReturnFromGraveyardToBattlefieldTargetEffect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        if (!effect.apply(game, source)) {
            return false;
        }

        for (Cost c : source.getCosts()) {
            /*   if (!c.getTargets().isEmpty()){
                UUID t = c.getTargets().getFirstTarget();
                Permanent exiled = game.getPermanentOrLKIBattlefield(t);*/
            if (c.isPaid() && c instanceof ExileTargetCost) {
                for (Permanent exiled : ((ExileTargetCost) c).getPermanents()) {
                    if (exiled != null) {
                        if (exiled.hasSubtype(SubType.THRULL, game)) {
                            game.getPermanent(source.getFirstTarget()).addCounters(CounterType.P2P2.createInstance(), source.getControllerId(), source, game);
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
