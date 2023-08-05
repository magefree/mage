
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author noxx
 */
public final class GallowsAtWillowHill extends CardImpl {

    private static final FilterControlledPermanent humanFilter = new FilterControlledPermanent("untapped Humans you control");

    static {
        humanFilter.add(TappedPredicate.UNTAPPED);
        humanFilter.add(SubType.HUMAN.getPredicate());
    }

    public GallowsAtWillowHill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, {tap}, Tap three untapped Humans you control: Destroy target creature. Its controller creates a 1/1 white Spirit creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GallowsAtWillowHillEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(3, 3, humanFilter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GallowsAtWillowHill(final GallowsAtWillowHill card) {
        super(card);
    }

    @Override
    public GallowsAtWillowHill copy() {
        return new GallowsAtWillowHill(this);
    }
}

class GallowsAtWillowHillEffect extends OneShotEffect {

    public GallowsAtWillowHillEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target creature. Its controller creates a 1/1 white Spirit creature token with flying";
    }

    public GallowsAtWillowHillEffect(final GallowsAtWillowHillEffect effect) {
        super(effect);
    }

    @Override
    public GallowsAtWillowHillEffect copy() {
        return new GallowsAtWillowHillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            permanent.destroy(source, game, false);
            if (controller != null) {
                CreateTokenTargetEffect effect = new CreateTokenTargetEffect(new SpiritWhiteToken());
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
