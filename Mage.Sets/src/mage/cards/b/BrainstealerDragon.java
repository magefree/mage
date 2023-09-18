package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrainstealerDragon extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("a nonland permanent an opponent owns");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public BrainstealerDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, exile the top card of each opponent's library. You may play those cards for as long as they remain exiled. If you cast a spell this way, you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new BrainstealerDragonExileEffect(), TargetController.YOU, false
        ));

        // Whenever a nonland permanent an opponent owns enters the battlefield under your control, they lose life equal to its mana value.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new BrainstealerDragonLifeEffect(), filter,
                false, SetTargetPointer.PERMANENT, null
        ));
    }

    private BrainstealerDragon(final BrainstealerDragon card) {
        super(card);
    }

    @Override
    public BrainstealerDragon copy() {
        return new BrainstealerDragon(this);
    }
}

class BrainstealerDragonExileEffect extends OneShotEffect {

    BrainstealerDragonExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of each opponent's library. You may " +
                "play those cards for as long as they remain exiled. If you cast a spell this way, " +
                "you may spend mana as though it were mana of any color to cast it";
    }

    private BrainstealerDragonExileEffect(final BrainstealerDragonExileEffect effect) {
        super(effect);
    }

    @Override
    public BrainstealerDragonExileEffect copy() {
        return new BrainstealerDragonExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .forEach(cards::add);
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(
                    game, source, card, Duration.Custom, true,
                    source.getControllerId(), null
            );
        }
        return true;
    }
}

class BrainstealerDragonLifeEffect extends OneShotEffect {

    BrainstealerDragonLifeEffect() {
        super(Outcome.Benefit);
        staticText = "they lose life equal to its mana value";
    }

    private BrainstealerDragonLifeEffect(final BrainstealerDragonLifeEffect effect) {
        super(effect);
    }

    @Override
    public BrainstealerDragonLifeEffect copy() {
        return new BrainstealerDragonLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getOwnerId());
        return player != null && player.loseLife(permanent.getManaValue(), game, source, false) > 0;
    }
}
