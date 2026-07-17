package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCloningOfShredder extends CardImpl {

    public TheCloningOfShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile target creature card from your graveyard. Create a token that's a copy of it, except it isn't legendary and is a Mutant in addition to its other types.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new TheCloningOfShredderFirstEffect(),
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );

        // II, III -- Create a token that's a copy of a card exiled with this Saga, except it isn't legendary and is a Mutant in addition to its other types.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new TheCloningOfShredderSecondEffect()
        );
        this.addAbility(sagaAbility);
    }

    private TheCloningOfShredder(final TheCloningOfShredder card) {
        super(card);
    }

    @Override
    public TheCloningOfShredder copy() {
        return new TheCloningOfShredder(this);
    }
}

class TheCloningOfShredderFirstEffect extends OneShotEffect {

    TheCloningOfShredderFirstEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from your graveyard. " +
                "Create a token that's a copy of it, except it isn't legendary " +
                "and is a Mutant in addition to its other types";
    }

    private TheCloningOfShredderFirstEffect(final TheCloningOfShredderFirstEffect effect) {
        super(effect);
    }

    @Override
    public TheCloningOfShredderFirstEffect copy() {
        return new TheCloningOfShredderFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        new CreateTokenCopyTargetEffect()
                .setIsntLegendary(true)
                .withAdditionalSubType(SubType.MUTANT)
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
        return true;
    }
}

class TheCloningOfShredderSecondEffect extends OneShotEffect {

    TheCloningOfShredderSecondEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of a card exiled with {this}, " +
                "except it isn't legendary and is a Mutant in addition to its other types";
    }

    private TheCloningOfShredderSecondEffect(final TheCloningOfShredderSecondEffect effect) {
        super(effect);
    }

    @Override
    public TheCloningOfShredderSecondEffect copy() {
        return new TheCloningOfShredderSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exileZone == null) {
            return false;
        }
        Card card;
        switch (exileZone.size()) {
            case 0:
                return false;
            case 1:
                card = exileZone.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
                target.withNotTarget(true);
                target.withChooseHint("to copy");
                player.choose(outcome, exileZone, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        new CreateTokenCopyTargetEffect()
                .setIsntLegendary(true)
                .withAdditionalSubType(SubType.MUTANT)
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
        return true;
    }
}
