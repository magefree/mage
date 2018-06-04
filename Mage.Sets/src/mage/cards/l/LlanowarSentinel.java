
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox

 */
public final class LlanowarSentinel extends CardImpl {

    public LlanowarSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Llanowar Sentinel enters the battlefield, you may pay {1}{G}. If you do, search your library for a card named Llanowar Sentinel and put that card onto the battlefield. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LlanowarSentinelEffect()));
    }

    public LlanowarSentinel(final LlanowarSentinel card) {
        super(card);
    }

    @Override
    public LlanowarSentinel copy() {
        return new LlanowarSentinel(this);
    }
}

class LlanowarSentinelEffect extends OneShotEffect {

    LlanowarSentinelEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {1}{G}. If you do, search your library for a card named Llanowar Sentinel and put that card onto the battlefield. Then shuffle your library";     }

    LlanowarSentinelEffect(final LlanowarSentinelEffect effect) {
        super(effect);
    }

    @Override
    public LlanowarSentinelEffect copy() {
        return new LlanowarSentinelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            if(player.chooseUse(Outcome.BoostCreature, "Do you want to to pay {1}{G}?", source, game)) {
                Cost cost = new ManaCostsImpl("{1}{G}");
                if(cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    FilterCard filter = new FilterCard("card named Llanowar Sentinel");
                    filter.add(new NamePredicate("Llanowar Sentinel"));
                    new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, filter), false, true).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }
}
