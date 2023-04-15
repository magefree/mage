package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class OmnathLocusOfAll extends CardImpl {

    public OmnathLocusOfAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B/P}{R}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If you would lose unspent mana, that mana becomes black instead.
        this.addAbility(new SimpleStaticAbility(new OmnathLocusOfAllManaEffect()));

        // At the beginning of your precombat main phase, look at the top card of your library. You may reveal that card
        // if it has three or more colored mana symbols in its mana cost. If you do, add three mana in any combination of
        // colors and put it into your hand. If you don’t reveal it, put it into your hand.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new OmnathLocusOfAllCardEffect(), TargetController.YOU, false
        ));
    }

    private OmnathLocusOfAll(final OmnathLocusOfAll card) {
        super(card);
    }

    @Override
    public OmnathLocusOfAll copy() {
        return new OmnathLocusOfAll(this);
    }
}

class OmnathLocusOfAllManaEffect extends ContinuousEffectImpl {

    OmnathLocusOfAllManaEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "if you would lose unspent mana, that mana becomes black instead";
    }

    private OmnathLocusOfAllManaEffect(final OmnathLocusOfAllManaEffect effect) {
        super(effect);
    }

    @Override
    public OmnathLocusOfAllManaEffect copy() {
        return new OmnathLocusOfAllManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().setManaBecomesBlack(true);
        }
        return true;
    }
}

class OmnathLocusOfAllCardEffect extends OneShotEffect {

    private static final String wubrg = "WUBRG";

    OmnathLocusOfAllCardEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top card of your library. You may reveal that card if it has three or more " +
                "colored mana symbols in its mana cost. If you do, add three mana in any combination of colors and " +
                "put it into your hand. If you don't reveal it, put it into your hand.";
    }

    private OmnathLocusOfAllCardEffect(final OmnathLocusOfAllCardEffect effect) {
        super(effect);
    }

    @Override
    public OmnathLocusOfAllCardEffect copy() {
        return new OmnathLocusOfAllCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards(null, card, game);
        if (card.getManaCostSymbols()
                .stream()
                .map(String::toCharArray)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .map(c -> "" + c)
                .filter(wubrg::contains)
                .count() >= 3
                && player.chooseUse(outcome, "Reveal " + card.getName() + '?', source, game)) {
            player.revealCards(source, new CardsImpl(card), game);
            new AddManaInAnyCombinationEffect(3).apply(game, source);
        }
        player.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}
