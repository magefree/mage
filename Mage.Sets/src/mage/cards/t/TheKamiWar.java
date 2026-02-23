package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheKamiWar extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls");
    private static final FilterPermanent filter2
            = new FilterNonlandPermanent("other target nonland permanent");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public TheKamiWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{W}{U}{B}{R}{G}",
                "O-Kagachi Made Manifest",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.DRAGON, SubType.SPIRIT}, "WUBRG"
        );

        // The Kami War
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Exile target nonland permanent an opponent controls.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ExileTargetEffect(), new TargetPermanent(filter)
        );

        // II — Return up to one other target nonland permanent to its owner's hand. Then each opponent discards a card.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new Effects(
                        new ReturnToHandTargetEffect(),
                        new DiscardEachPlayerEffect(TargetController.OPPONENT)
                                .concatBy("Then")
                ), new TargetPermanent(0, 1, filter2)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // O-Kagachi Made Manifest
        this.getRightHalfCard().setPT(6, 6);

        // O-Kagachi Made Manifest is all colors.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("{this} is all colors")));

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever O-Kagachi Made Manifest attacks, defending player chooses a nonland card in your graveyard. Return that card to your hand. O-Kagachi Made Manifest gets +X/+0 until end of turn, where X is the mana value of that card.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(
                new OKagachiMadeManifestEffect(), false, null, SetTargetPointer.PLAYER
        ));
    }

    private TheKamiWar(final TheKamiWar card) {
        super(card);
    }

    @Override
    public TheKamiWar copy() {
        return new TheKamiWar(this);
    }
}

class OKagachiMadeManifestEffect extends OneShotEffect {

    OKagachiMadeManifestEffect() {
        super(Outcome.Benefit);
        staticText = "defending player chooses a nonland card in your graveyard. Return that card to your hand. " +
                "{this} gets +X/+0 until end of turn, where X is the mana value of that card";
    }

    private OKagachiMadeManifestEffect(final OKagachiMadeManifestEffect effect) {
        super(effect);
    }

    @Override
    public OKagachiMadeManifestEffect copy() {
        return new OKagachiMadeManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card;
        switch (controller.getGraveyard().count(StaticFilters.FILTER_CARD_A_NON_LAND, game)) {
            case 0:
                return false;
            case 1:
                card = controller
                        .getGraveyard()
                        .getCards(StaticFilters.FILTER_CARD_A_NON_LAND, game)
                        .stream()
                        .findFirst()
                        .orElse(null);
                break;
            default:
                TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_A_NON_LAND);
                target.withNotTarget(true);
                player.choose(Outcome.ReturnToHand, controller.getGraveyard(), target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        int manaValue = card.getManaValue();
        player.moveCards(card, Zone.HAND, source, game);
        if (manaValue > 0) {
            game.addEffect(new BoostSourceEffect(manaValue, 0, Duration.EndOfTurn), source);
        }
        return true;
    }
}
