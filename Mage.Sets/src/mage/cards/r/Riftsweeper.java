
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public final class Riftsweeper extends CardImpl {

    private static final FilterCard filter = new FilterCard("face-up exiled card");
    static {
        filter.add(Predicates.not(new FaceDownPredicate()));
    }

    public Riftsweeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Riftsweeper enters the battlefield, choose target face-up exiled card. Its owner shuffles it into their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RiftsweeperEffect(), false);
        ability.addTarget(new TargetCardInExile(1, 1, filter, null, true));
        this.addAbility(ability);
    }

    public Riftsweeper(final Riftsweeper card) {
        super(card);
    }

    @Override
    public Riftsweeper copy() {
        return new Riftsweeper(this);
    }
}

class RiftsweeperEffect extends OneShotEffect {

    public RiftsweeperEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose target face-up exiled card. Its owner shuffles it into their library";
    }

    public RiftsweeperEffect(final RiftsweeperEffect effect) {
        super(effect);
    }

    @Override
    public RiftsweeperEffect copy() {
        return new RiftsweeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            // remove exiting suspend counters
            card.getCounters(game).clear();
            // move to exile
            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            game.getPlayer(card.getOwnerId()).shuffleLibrary(source, game);
            game.informPlayers("Riftsweeper: Choosen card was " + card.getName());
            return true;
        }
        return false;
    }
}
