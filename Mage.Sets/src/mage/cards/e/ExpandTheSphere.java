package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

public class ExpandTheSphere extends CardImpl {
    public ExpandTheSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        //Look at the top six cards of your library. Put up to two land cards from among them onto the battlefield
        //tapped and the rest on the bottom of your library in a random order. If you put fewer than two lands onto the
        //battlefield this way, proliferate a number of times equal to the difference.
        this.getSpellAbility().addEffect(new ExpandTheSphereEffect());
    }

    private ExpandTheSphere(final ExpandTheSphere card) {
        super(card);
    }

    @Override
    public ExpandTheSphere copy() {
        return new ExpandTheSphere(this);
    }
}

class ExpandTheSphereEffect extends LookLibraryControllerEffect {
    public ExpandTheSphereEffect() {
        super(6);
        staticText = "Look at the top six cards of your library. Put up to two land cards from among them onto the " +
                "battlefield tapped and the rest on the bottom of your library in a random order. If you put fewer " +
                "than two lands onto the battlefield this way, proliferate a number of times equal to the difference. " +
                "<i>(Choose any number of permanents and/or players, then give each another counter of each kind " +
                "already there.)</i>";
    }

    private ExpandTheSphereEffect(final ExpandTheSphereEffect effect) {
        super(effect);
    }

    @Override
    protected boolean actionWithLookedCards(Game game, Ability source, Player player, Cards cards) {
        TargetCard target = new TargetCard(0, 2, Zone.LIBRARY, StaticFilters.FILTER_CARD_LAND);
        target.withChooseHint("to put " + PutCards.BATTLEFIELD_TAPPED.getMessage(false, false));
        Cards pickedCards;
        if (!player.chooseTarget(PutCards.BATTLEFIELD_TAPPED.getOutcome(), cards, target, source, game)) {
            pickedCards = new CardsImpl();
        }
        else {
            pickedCards = new CardsImpl(target.getTargets());
        }
        cards.removeAll(pickedCards);
        boolean result = PutCards.BATTLEFIELD_TAPPED.moveCards(player, pickedCards, source, game);
        result |= PutCards.BOTTOM_RANDOM.moveCards(player, cards, source, game);
        if (pickedCards.size() < 2) {
            new ProliferateEffect(true).apply(game, source);
        }
        if (pickedCards.size() < 1) {
            new ProliferateEffect(true).apply(game, source);
        }
        return result;
    }

    @Override
    public ExpandTheSphereEffect copy() {
        return new ExpandTheSphereEffect(this);
    }
}