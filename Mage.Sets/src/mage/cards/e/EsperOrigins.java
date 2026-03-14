package mage.cards.e;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EsperOrigins extends TransformingDoubleFacedCard {

    public EsperOrigins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.SORCERY}, new SubType[]{}, "{1}{G}",
                "Summon: Esper Maduin",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.ELEMENTAL}, "G"
        );

        // Esper Origins
        // Surveil 2. You gain 2 life. If this spell was cast from a graveyard, exile it, then put it onto the battlefield transformed under its owner's control with a finality counter on it.
        this.getLeftHalfCard().getSpellAbility().addEffect(new SurveilEffect(2, false));
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getLeftHalfCard().getSpellAbility().addEffect(new EsperOriginsEffect());

        // Flashback {3}{G}
        this.getLeftHalfCard().addAbility(new FlashbackAbility(this.getLeftHalfCard(), new ManaCostsImpl<>("{3}{G}")));

        // Summon: Esper Maduin
        this.getRightHalfCard().setPT(4, 4);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I -- Reveal the top card of your library. If it's a permanent card, put it into your hand.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, new SummonEsperMaduinEffect());

        // II -- Add {G}{G}.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_II, new BasicManaEffect(Mana.GreenMana(2)));

        // III -- Other creatures you control get +2/+2 and gain trample until end of turn.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_III,
                new BoostControlledEffect(
                        2, 2, Duration.EndOfTurn, true
                ).setText("other creatures you control get +2/+2"),
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE, true
                ).setText("and gain trample until end of turn")
        );
        this.getRightHalfCard().addAbility(sagaAbility.withShowSacText(true));
    }

    private EsperOrigins(final EsperOrigins card) {
        super(card);
    }

    @Override
    public EsperOrigins copy() {
        return new EsperOrigins(this);
    }
}

class EsperOriginsEffect extends OneShotEffect {

    EsperOriginsEffect() {
        super(Outcome.Benefit);
        staticText = "If this spell was cast from a graveyard, exile it, then put it onto the battlefield " +
                "transformed under its owner's control with a finality counter on it.";
    }

    private EsperOriginsEffect(final EsperOriginsEffect effect) {
        super(effect);
    }

    @Override
    public EsperOriginsEffect copy() {
        return new EsperOriginsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!CastFromGraveyardSourceCondition.instance.apply(game, source)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = game.getSpell(source.getId());
        if (player == null || spell == null) {
            return false;
        }
        Card card = spell.getMainCard();
        player.moveCards(card, Zone.EXILED, source, game);
        game.setEnterWithCounters(card.getId(), new Counters(CounterType.FINALITY.createInstance()));
        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
        player.moveCards(
                card, Zone.BATTLEFIELD, source, game, false,
                false, true, null
        );
        return true;
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
