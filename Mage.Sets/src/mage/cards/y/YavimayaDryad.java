
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox
 */
public final class YavimayaDryad extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("a Forest card");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public YavimayaDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
        // When Yavimaya Dryad enters the battlefield, you may search your library for a Forest card and put it onto the battlefield tapped under target player's control. If you do, shuffle your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new YavimayaDryadEffect(new TargetCardInLibrary(filter)), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private YavimayaDryad(final YavimayaDryad card) {
        super(card);
    }

    @Override
    public YavimayaDryad copy() {
        return new YavimayaDryad(this);
    }
}

class YavimayaDryadEffect extends SearchEffect {

    public YavimayaDryadEffect(TargetCardInLibrary target) {
        super(target, Outcome.PutLandInPlay);
        staticText = "you may search your library for a Forest card, put it onto the battlefield tapped under target player's control, then shuffle";
    }

    public YavimayaDryadEffect(final YavimayaDryadEffect effect) {
        super(effect);
    }

    @Override
    public YavimayaDryadEffect copy() {
        return new YavimayaDryadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                targetPlayer.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                        Zone.BATTLEFIELD, source, game, true, false, false, null);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
