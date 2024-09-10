
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VectisDominator extends CardImpl {

    public VectisDominator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Tap target creature unless its controller pays 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VectisDominatorEffect(new PayLifeCost(2)), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VectisDominator(final VectisDominator card) {
        super(card);
    }

    @Override
    public VectisDominator copy() {
        return new VectisDominator(this);
    }
}

class VectisDominatorEffect extends OneShotEffect {

    protected Cost cost;

    public VectisDominatorEffect(Cost cost) {
        super(Outcome.Detriment);
        this.staticText = "Tap target creature unless its controller pays 2 life";
        this.cost = cost;
    }

    private VectisDominatorEffect(final VectisDominatorEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public VectisDominatorEffect copy() {
        return new VectisDominatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            Player player = game.getPlayer(targetCreature.getControllerId());
            if (player != null) {
                cost.clearPaid();
                String question = "Pay 2 life? (Otherwise " + targetCreature.getName()+" will be tapped)";
                if (player.chooseUse(Outcome.Benefit, question, source, game)) {
                    cost.pay(source, game, source, targetCreature.getControllerId(), true, null);
                }
                if (!cost.isPaid()) {
                    return targetCreature.tap(source, game);
                }
            }
        }
        return false;
    }
}
