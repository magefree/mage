package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jimga150
 */
public final class PantlazaSunFavored extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DINOSAUR, "Dinosaur");

    public PantlazaSunFavored(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Pantlaza, Sun-Favored or another Dinosaur enters the battlefield under your control,
        // you may discover X, where X is that creature's toughness. Do this only once each turn.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new PantlazaSunFavoredEffect(), filter, true, SetTargetPointer.PERMANENT, true
        ).setDoOnlyOnceEachTurn(true));
    }

    private PantlazaSunFavored(final PantlazaSunFavored card) {
        super(card);
    }

    @Override
    public PantlazaSunFavored copy() {
        return new PantlazaSunFavored(this);
    }
}

// Based on Dinosaur Egg
class PantlazaSunFavoredEffect extends OneShotEffect {

    PantlazaSunFavoredEffect() {
        super(Outcome.PlayForFree);
        staticText = "discover X, where X is that creature's toughness";
    }

    private PantlazaSunFavoredEffect(final PantlazaSunFavoredEffect effect) {
        super(effect);
    }

    @Override
    public PantlazaSunFavoredEffect copy() {
        return new PantlazaSunFavoredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (player == null || permanent == null) {
            return false;
        }
        DiscoverEffect.doDiscover(player, Math.max(0, permanent.getToughness().getValue()), game, source);
        return true;
    }
}
