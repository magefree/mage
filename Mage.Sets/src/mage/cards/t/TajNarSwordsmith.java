package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class TajNarSwordsmith extends CardImpl {

    public TajNarSwordsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Taj-Nar Swordsmith enters the battlefield, you may pay {X}. If you do, search your library for an Equipment card with converted mana cost X or less and put that card onto the battlefield. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TajNarSwordsmithEffect()));
    }

    private TajNarSwordsmith(final TajNarSwordsmith card) {
        super(card);
    }

    @Override
    public TajNarSwordsmith copy() {
        return new TajNarSwordsmith(this);
    }
}

class TajNarSwordsmithEffect extends OneShotEffect {

    TajNarSwordsmithEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}. If you do, search your library for an Equipment card with mana value X or less, put that card onto the battlefield, then shuffle";
    }

    TajNarSwordsmithEffect(final TajNarSwordsmithEffect effect) {
        super(effect);
    }

    @Override
    public TajNarSwordsmithEffect copy() {
        return new TajNarSwordsmithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.chooseUse(Outcome.Benefit, "Do you want to pay {X} to search and put Equipment?", source, game)) {
            // can be zero
            int payCount = ManaUtil.playerPaysXGenericMana(true, "Taj-Nar Swordsmith", player, source, game);
            FilterCard filter = new FilterCard("Equipment card with mana value {" + payCount + "} or less");
            filter.add(SubType.EQUIPMENT.getPredicate());
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, payCount + 1));
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, filter), false).apply(game, source);
            return true;
        }
        return false;
    }
}
