package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonEsperMaduin extends CardImpl {

    public SummonEsperMaduin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.nightCard = true;
        this.color.setGreen(true);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Reveal the top card of your library. If it's a permanent card, put it into your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new SummonEsperMaduinEffect());

        // II -- Add {G}{G}.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new BasicManaEffect(Mana.GreenMana(2)));

        // III -- Other creatures you control get +2/+2 and gain trample until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new BoostControlledEffect(
                        2, 2, Duration.EndOfTurn, true
                ).setText("other creatures you control get +2/+2"),
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE, true
                ).setText("and gain trample until end of turn")
        );
        this.addAbility(sagaAbility.withShowSacText(true));
    }

    private SummonEsperMaduin(final SummonEsperMaduin card) {
        super(card);
    }

    @Override
    public SummonEsperMaduin copy() {
        return new SummonEsperMaduin(this);
    }
}

class SummonEsperMaduinEffect extends OneShotEffect {

    SummonEsperMaduinEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. If it's a permanent card, put it into your hand";
    }

    private SummonEsperMaduinEffect(final SummonEsperMaduinEffect effect) {
        super(effect);
    }

    @Override
    public SummonEsperMaduinEffect copy() {
        return new SummonEsperMaduinEffect(this);
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
        player.revealCards(source, new CardsImpl(card), game);
        if (card.isPermanent(game)) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
