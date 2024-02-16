
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class BazaarTrader extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact, creature, or land you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public BazaarTrader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target player gains control of target artifact, creature, or land you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BazaarTraderEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private BazaarTrader(final BazaarTrader card) {
        super(card);
    }

    @Override
    public BazaarTrader copy() {
        return new BazaarTrader(this);
    }
}

class BazaarTraderEffect extends ContinuousEffectImpl {

    MageObjectReference targetPermanentReference;
    
    public BazaarTraderEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "Target player gains control of target artifact, creature, or land you control";
    }

    private BazaarTraderEffect(final BazaarTraderEffect effect) {
        super(effect);
        this.targetPermanentReference = effect.targetPermanentReference;
    }

    @Override
    public BazaarTraderEffect copy() {
        return new BazaarTraderEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        targetPermanentReference = new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent permanent = targetPermanentReference.getPermanent(game);
        if (player != null && permanent != null) {
            return permanent.changeControllerId(player.getId(), game, source);            
        } else {
            discard();
        }
        return false;
    }
}
