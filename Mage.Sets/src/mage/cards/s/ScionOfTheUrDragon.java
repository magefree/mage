
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author duncant
 */
public final class ScionOfTheUrDragon extends CardImpl {

    public ScionOfTheUrDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // {2}: Search your library for a Dragon permanent card and put it into your graveyard. If you do, Scion of the Ur-Dragon becomes a copy of that card until end of turn. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ScionOfTheUrDragonEffect(),
                new ManaCostsImpl<>("{2}")));
    }

    private ScionOfTheUrDragon(final ScionOfTheUrDragon card) {
        super(card);
    }

    @Override
    public ScionOfTheUrDragon copy() {
        return new ScionOfTheUrDragon(this);
    }
}

class ScionOfTheUrDragonEffect extends SearchEffect {

    private static final FilterCard filter = new FilterPermanentCard("Dragon permanent card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public ScionOfTheUrDragonEffect() {
        super(new TargetCardInLibrary(filter), Outcome.Copy);
        staticText = "Search your library for a Dragon permanent card and put it into your graveyard. If you do, {this} becomes a copy of that card until end of turn. Then shuffle.";
    }

    ScionOfTheUrDragonEffect(final ScionOfTheUrDragonEffect effect) {
        super(effect);
    }

    @Override
    public ScionOfTheUrDragonEffect copy() {
        return new ScionOfTheUrDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            if (player.searchLibrary(target, source, game)) {
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        player.moveCards(card, Zone.GRAVEYARD, source, game);
                        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, card, source.getSourceId());
                        game.addEffect(copyEffect, source);
                    }
                }
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
