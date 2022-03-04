
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class ArcumDagsson extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact creature");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public ArcumDagsson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Target artifact creature's controller sacrifices it. That player may search their library for a noncreature artifact card, put it onto the battlefield, then shuffle their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ArcumDagssonEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ArcumDagsson(final ArcumDagsson card) {
        super(card);
    }

    @Override
    public ArcumDagsson copy() {
        return new ArcumDagsson(this);
    }
}

class ArcumDagssonEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard("noncreature artifact card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    ArcumDagssonEffect() {
        super(Outcome.Removal);
        this.staticText = "Target artifact creature's controller sacrifices it. That player may search their library for a noncreature artifact card, put it onto the battlefield, then shuffle";
    }

    ArcumDagssonEffect(final ArcumDagssonEffect effect) {
        super(effect);
    }

    @Override
    public ArcumDagssonEffect copy() {
        return new ArcumDagssonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent artifactCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (artifactCreature != null) {
            Player player = game.getPlayer(artifactCreature.getControllerId());
            if (player != null) {
                artifactCreature.sacrifice(source, game);
                game.getState().processAction(game);  // Workaround for https://github.com/magefree/mage/issues/8501
                if (player.chooseUse(Outcome.PutCardInPlay, "Search your library for a noncreature artifact card?", source, game)) {
                    TargetCardInLibrary target = new TargetCardInLibrary(filter);
                    if (player.searchLibrary(target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            player.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                    player.shuffleLibrary(source, game);
                }
                return true;
            }
        }
        return false;
    }
}
