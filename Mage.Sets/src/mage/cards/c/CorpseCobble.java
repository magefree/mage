package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieMenaceToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class CorpseCobble extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("any number of creatures");

    public CorpseCobble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // As an additional cost to cast this spell, sacrifice any number of creatures.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true)));

        // Create an X/X blue and black Zombie creature token with menace, where X is the total power of the sacrificed creatures.
        this.getSpellAbility().addEffect(new CorpseCobbleEffect());

        // Flashback {3}{U}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{3}{U}{B}"), TimingRule.INSTANT));
    }

    private CorpseCobble(final CorpseCobble card) {
        super(card);
    }

    @Override
    public CorpseCobble copy() {
        return new CorpseCobble(this);
    }
}

class CorpseCobbleEffect extends OneShotEffect {

    public CorpseCobbleEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X blue and black Zombie creature token with menace, where X is the total power of the sacrificed creatures";
    }

    private CorpseCobbleEffect(final CorpseCobbleEffect effect) {
        super(effect);
    }

    @Override
    public CorpseCobbleEffect copy() {
        return new CorpseCobbleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                for (Permanent permanent : ((SacrificeTargetCost) cost).getPermanents()) {
                    xValue += permanent.getPower().getValue();
                }
            }
        }
        return new ZombieMenaceToken(xValue).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
