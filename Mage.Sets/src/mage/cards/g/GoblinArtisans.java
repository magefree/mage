
package mage.cards.g;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;

/**
 *
 * @author MarcoMarin
 */
public final class GoblinArtisans extends CardImpl {

    public GoblinArtisans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Flip a coin. If you win the flip, draw a card. If you lose the flip, counter target artifact spell you control that isn't the target of an ability from another creature named Goblin Artisans.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinArtisansEffect(), new TapSourceCost()));

    }

    public GoblinArtisans(final GoblinArtisans card) {
        super(card);
    }

    @Override
    public GoblinArtisans copy() {
        return new GoblinArtisans(this);
    }
}

class GoblinArtisansEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanent named Goblin Artisans");

    static {
        filter.add(new NamePredicate("Goblin Artisans"));
    }

    public GoblinArtisansEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, draw a card. If you lose the flip, counter target artifact spell you control that isn't the target of an ability from another creature named Goblin Artisans.";
    }

    public GoblinArtisansEffect(GoblinArtisansEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(source, game, true)) {
                controller.drawCards(1, game);
            } else {
                List<Permanent> artifacts = game.getBattlefield().getActivePermanents(new FilterControlledArtifactPermanent(), source.getControllerId(), game);
                if (artifacts.isEmpty()) {//Don't even bother if there is no artifact to 'counter'/sacrifice
                    return true;
                }

                filter.add(Predicates.not(new PermanentIdPredicate(source.getSourceId())));
                //removed the activating instance of Artisans, btw, wasn't that filter declared as static final? How come I can do this here? :)
                List<Permanent> list = game.getBattlefield().getAllActivePermanents(filter, game);
                for (Permanent perm : list) { // should I limit below for a particular kind of ability? Going for the most general, it's unlikely there'll be any other artisans anyway, so not concerned about efficiency :p
                    for (Ability abil : perm.getAbilities(game)) {//below is copied from TargetsPermanentPredicate, but why only "selectedModes"? Shouldnt be more general as well?
                        for (UUID modeId : abil.getModes().getSelectedModes()) {
                            Mode mode = abil.getModes().get(modeId);
                            for (Target target : mode.getTargets()) {
                                for (UUID targetId : target.getTargets()) {
                                    artifacts.remove(game.getPermanentOrLKIBattlefield(targetId));
                                }// we could
                            }// remove this
                        }//closing bracers
                    }// pyramid, if it's bothering anyone
                } //they are all one-liners after all :)
                if (!artifacts.isEmpty()) {
                    Cards cards = new CardsImpl();
                    for (Permanent perm : artifacts) {
                        cards.add(perm.getId());
                    }
                    TargetCard target = new TargetCard(Zone.BATTLEFIELD, new FilterCard());
                    controller.choose(Outcome.Sacrifice, cards, target, game);
                    game.getPermanent(target.getFirstTarget()).sacrifice(source.getSourceId(), game);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public GoblinArtisansEffect copy() {
        return new GoblinArtisansEffect(this);
    }
}
