package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

public class ContagiousVorrac extends CardImpl {
    public ContagiousVorrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.BOAR);
        this.addSubType(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //When Contagious Vorrac enters the battlefield, look at the top four cards of your library. You may reveal a
        //land card from among them and put it into your hand. Put the rest on the bottom of your library in a random
        //order. If you didn't put a card into your hand this way, proliferate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ContagiousVorracEffect()));
    }

    private ContagiousVorrac(final ContagiousVorrac card) {
        super(card);
    }

    @Override
    public ContagiousVorrac copy() {
        return new ContagiousVorrac(this);
    }
}

class ContagiousVorracEffect extends LookLibraryControllerEffect {
    public ContagiousVorracEffect() {
        super(4);
        staticText = "look at the top four cards of your library. You may reveal a land card from among them and put " +
                "it into your hand. Put the rest on the bottom of your library in a random order. If you didn't put " +
                "a card into your hand this way, proliferate. <i>(Choose any number of permanents and/or players, " +
                "then give each another counter of each kind already there.)</i>";
    }

    private ContagiousVorracEffect(final ContagiousVorracEffect effect) {
        super(effect);
    }

    @Override
    protected boolean actionWithLookedCards(Game game, Ability source, Player player, Cards cards) {
        TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, StaticFilters.FILTER_CARD_LAND);
        target.withChooseHint("to reveal and put " + PutCards.HAND.getMessage(false, false));
        Cards pickedCards;
        if (!player.chooseTarget(PutCards.HAND.getOutcome(), cards, target, source, game)) {
            pickedCards = new CardsImpl();
        }
        else {
            pickedCards = new CardsImpl(target.getTargets());
            player.revealCards(source, pickedCards, game);
        }
        cards.removeAll(pickedCards);
        boolean result = PutCards.HAND.moveCards(player, pickedCards, source, game);
        result |= PutCards.BOTTOM_RANDOM.moveCards(player, cards, source, game);
        if (pickedCards.size() < 1) {
            new ProliferateEffect(true).apply(game, source);
        }
        return result;
    }

    @Override
    public ContagiousVorracEffect copy() {
        return new ContagiousVorracEffect(this);
    }
}
