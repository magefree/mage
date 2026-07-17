package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonEsperValigarmanda extends CardImpl {

    public SummonEsperValigarmanda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Exile an instant or sorcery card from each graveyard.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new SummonEsperValigarmandaExileEffect()
        );

        // II, III, IV -- Add {R} for each lore counter on this Saga. You may cast an instant or sorcery card exiled with this Saga, and mana of any type can be spent to cast that spell.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_IV,
                new SummonEsperValigarmandaCastEffect()
        );
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private SummonEsperValigarmanda(final SummonEsperValigarmanda card) {
        super(card);
    }

    @Override
    public SummonEsperValigarmanda copy() {
        return new SummonEsperValigarmanda(this);
    }
}

class SummonEsperValigarmandaExileEffect extends OneShotEffect {

    SummonEsperValigarmandaExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile an instant or sorcery card from each graveyard";
    }

    private SummonEsperValigarmandaExileEffect(final SummonEsperValigarmandaExileEffect effect) {
        super(effect);
    }

    @Override
    public SummonEsperValigarmandaExileEffect copy() {
        return new SummonEsperValigarmandaExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
        target.withNotTarget(true);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game) < 1) {
                continue;
            }
            target.clearChosen();
            controller.choose(outcome, player.getGraveyard(), target, source, game);
            cards.add(game.getCard(target.getFirstTarget()));
        }
        return !cards.isEmpty()
                && controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }
}

class SummonEsperValigarmandaCastEffect extends OneShotEffect {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.LORE);

    SummonEsperValigarmandaCastEffect() {
        super(Outcome.Benefit);
        staticText = "add {R} for each lore counter on {this}. " +
                "You may cast an instant or sorcery card exiled with {this}, " +
                "and mana of any type can be spent to cast that spell";
    }

    private SummonEsperValigarmandaCastEffect(final SummonEsperValigarmandaCastEffect effect) {
        super(effect);
    }

    @Override
    public SummonEsperValigarmandaCastEffect copy() {
        return new SummonEsperValigarmandaCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = xValue.calculate(game, source, this);
        if (count > 0) {
            player.getManaPool().addMana(Mana.RedMana(count), game, source);
        }
        TargetCard target = new TargetCardInExile(
                0, 1,
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY,
                CardUtil.getExileZoneId(game, source)
        );
        target.withNotTarget(true);
        target.withChooseHint("to cast");
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null
                && new MayCastTargetCardEffect(CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE)
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
    }
}
