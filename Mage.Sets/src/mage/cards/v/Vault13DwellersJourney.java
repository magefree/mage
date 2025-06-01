package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Vault13DwellersJourney extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchantment or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ENCHANTMENT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public Vault13DwellersJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- For each player, exile up to one other target enchantment or creature that player controls until Vault 13 leaves the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                triggeredAbility -> {
                    triggeredAbility.addEffect(new ExileUntilSourceLeavesEffect()
                            .setText("for each player, exile up to one other target enchantment or " +
                                    "creature that player controls until {this} leaves the battlefield")
                            .setTargetPointer(new EachTargetPointer()));
                    triggeredAbility.addTarget(new TargetPermanent(0, 1, filter));
                    triggeredAbility.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, false));
                }
        );

        // II -- You gain 2 life and scry 2.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, new GainLifeEffect(2),
                new ScryEffect(2, false).concatBy("and")
        );

        // III -- Return two cards exiled with Vault 13 to the battlefield under their owners' control and put the rest on the bottom of their owners' libraries.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new Vault13DwellersJourneyEffect());
        this.addAbility(sagaAbility);
    }

    private Vault13DwellersJourney(final Vault13DwellersJourney card) {
        super(card);
    }

    @Override
    public Vault13DwellersJourney copy() {
        return new Vault13DwellersJourney(this);
    }
}

class Vault13DwellersJourneyEffect extends OneShotEffect {

    Vault13DwellersJourneyEffect() {
        super(Outcome.Benefit);
        staticText = "return two cards exiled with {this} to the battlefield " +
                "under their owners' control and put the rest on the bottom of their owners' libraries";
    }

    private Vault13DwellersJourneyEffect(final Vault13DwellersJourneyEffect effect) {
        super(effect);
    }

    @Override
    public Vault13DwellersJourneyEffect copy() {
        return new Vault13DwellersJourneyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl();
        Optional.ofNullable(game.getExile().getExileZone(CardUtil.getExileZoneId(game, source)))
                .ifPresent(cards::addAll);
        if (player == null || cards.isEmpty()) {
            return false;
        }
        Cards toPlay = new CardsImpl();
        if (cards.size() > 2) {
            TargetCard target = new TargetCardInExile(2, 2, StaticFilters.FILTER_CARD_CARDS);
            target.withNotTarget(true);
            target.withChooseHint("to return to the battlefield");
            player.choose(outcome, cards, target, source, game);
            toPlay.addAll(target.getTargets());
        } else {
            toPlay.addAll(cards);
        }
        toPlay.retainZone(Zone.EXILED, game);
        if (!toPlay.isEmpty()) {
            player.moveCards(
                    toPlay.getCards(game), Zone.BATTLEFIELD, source, game,
                    false, false, true, null
            );
            cards.retainZone(Zone.EXILED, game);
        }
        if (!cards.isEmpty()) {
            Effect e = new PutOnLibraryTargetEffect(false);
            e.setTargetPointer(new FixedTargets(cards, game));
            e.apply(game, source);
        }
        return true;
    }
}
