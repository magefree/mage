
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class InameDeathAspect extends CardImpl {

    public InameDeathAspect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InameDeathAspectEffect(), true));
    }

    public InameDeathAspect(final InameDeathAspect card) {
        super(card);
    }

    @Override
    public InameDeathAspect copy() {
        return new InameDeathAspect(this);
    }
}

class InameDeathAspectEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public InameDeathAspectEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.Neutral);
        staticText = "search your library for any number of Spirit cards and put them into your graveyard. If you do, shuffle your library";
    }

    public InameDeathAspectEffect(final InameDeathAspectEffect effect) {
        super(effect);
    }

    @Override
    public InameDeathAspectEffect copy() {
        return new InameDeathAspectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                player.moveCards(new CardsImpl(target.getTargets()), Zone.GRAVEYARD, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}