
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EmrakulsEvangel extends CardImpl {

    public EmrakulsEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {T}, Sacrifice Emrakul's Evangel and any number of other non-Eldrazi creatures:
        // Create a 3/2 colorless Eldrazi Horror creature token for each creature sacrificed this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EmrakulsEvangelEffect(), new TapSourceCost());
        ability.addCost(new EmrakulsEvangelCost());
        this.addAbility(ability);
    }

    private EmrakulsEvangel(final EmrakulsEvangel card) {
        super(card);
    }

    @Override
    public EmrakulsEvangel copy() {
        return new EmrakulsEvangel(this);
    }
}

class EmrakulsEvangelCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Eldrazi creatures you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(SubType.ELDRAZI.getPredicate()));
    }

    private int numSacrificed = 1; // always sacrifices self at least

    public EmrakulsEvangelCost() {
        this.text = "Sacrifice {this} and any number of other non-Eldrazi creatures";
    }

    private EmrakulsEvangelCost(final EmrakulsEvangelCost cost) {
        super(cost);
        this.numSacrificed = cost.getNumSacrificed();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent selfPermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(controllerId);
        if (selfPermanent != null && player != null) {
            paid = selfPermanent.sacrifice(source, game); // sacrifice self
            Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
            player.chooseTarget(Outcome.Sacrifice, target, ability, game);
            for (UUID permanentId : target.getTargets()) {
                Permanent otherPermanent = game.getPermanent(permanentId);
                if (otherPermanent != null) {
                    if (otherPermanent.sacrifice(source, game)) {
                        numSacrificed++;
                    }
                }
            }
        }
        return paid;
    }

    public int getNumSacrificed() {
        return numSacrificed;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && game.getPlayer(controllerId).canPaySacrificeCost(permanent, source, controllerId, game);
    }

    @Override
    public EmrakulsEvangelCost copy() {
        return new EmrakulsEvangelCost(this);
    }
}

class EmrakulsEvangelEffect extends OneShotEffect {

    EmrakulsEvangelEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Create a 3/2 colorless Eldrazi Horror creature token for each creature sacrificed this way.";
    }

    private EmrakulsEvangelEffect(final EmrakulsEvangelEffect effect) {
        super(effect);
    }

    @Override
    public EmrakulsEvangelEffect copy() {
        return new EmrakulsEvangelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int tokensToCreate = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof EmrakulsEvangelCost) {
                    tokensToCreate = ((EmrakulsEvangelCost) cost).getNumSacrificed();
                }
            }
            if (tokensToCreate > 0) {
                EldraziHorrorToken token = new EldraziHorrorToken();
                token.putOntoBattlefield(tokensToCreate, game, source, source.getControllerId());
            }
            return true;
        }
        return false;
    }
}
