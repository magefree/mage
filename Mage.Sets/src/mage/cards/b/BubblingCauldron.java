package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class BubblingCauldron extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature named Festering Newt");

    static {
        filter.add(new NamePredicate("Festering Newt"));
    }

    public BubblingCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}, Sacrifice a creature: You gain 4 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(4), new ManaCostsImpl<>("{1}"));
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability1);
        // {1}, {T}, Sacrifice a creature named Festering Newt: Each opponent loses 4 life. You gain life equal to the life lost this way.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BubblingCauldronEffect(), new ManaCostsImpl<>("{1}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability2);
    }

    private BubblingCauldron(final BubblingCauldron card) {
        super(card);
    }

    @Override
    public BubblingCauldron copy() {
        return new BubblingCauldron(this);
    }
}

class BubblingCauldronEffect extends OneShotEffect {

    public BubblingCauldronEffect() {
        super(Outcome.GainLife);
        staticText = "Each opponent loses 4 life. You gain life equal to the life lost this way";
    }

    public BubblingCauldronEffect(final BubblingCauldronEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lostLife = 0;
        Player controller = game.getPlayer(source.getControllerId());
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                lostLife += opponent.loseLife(4, game, source, false);
            }
        }
        if (controller != null) {
            controller.gainLife(lostLife, game, source);
        }
        return true;
    }

    @Override
    public BubblingCauldronEffect copy() {
        return new BubblingCauldronEffect(this);
    }

}
