package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.WhenYouAttackDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author miesma
 */
public final class RoadsGoEverEverOn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Plains you control");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Plains you control", xValue);

    public RoadsGoEverEverOn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I — Search your library for up to two basic Plains cards, exile them, then shuffle. You gain 2 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new RoadsGoEverEverOnSearchAndExileEffect(), new GainLifeEffect(2));

        // II, III — Put a card exiled with this Saga into its owner’s hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, new RoadsGoEverEverOnPutInHandEffect());

        // IV — Whenever you attack this turn, target creature you control gets +1/+1 until end of turn for each Plains you control.
        DelayedTriggeredAbility boostAbility = new WhenYouAttackDelayedTriggeredAbility(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn));
        boostAbility.addTarget(new TargetControlledCreaturePermanent());
        boostAbility.addHint(hint);
        Effect attackEffect = new CreateDelayedTriggeredAbilityEffect(boostAbility);
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, attackEffect);
        this.addAbility(sagaAbility);
    }

    private RoadsGoEverEverOn(final RoadsGoEverEverOn card) {
        super(card);
    }

    @Override
    public RoadsGoEverEverOn copy() {
        return new RoadsGoEverEverOn(this);
    }
}

class RoadsGoEverEverOnSearchAndExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Plains card");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    RoadsGoEverEverOnSearchAndExileEffect() {
        super(Outcome.Neutral);
        this.staticText = "search your library for up to two basic Plains cards, exile them, then shuffle";
    }

    private RoadsGoEverEverOnSearchAndExileEffect(final RoadsGoEverEverOnSearchAndExileEffect effect) {
        super(effect);
    }

    @Override
    public RoadsGoEverEverOnSearchAndExileEffect copy() {
        return new RoadsGoEverEverOnSearchAndExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, filter);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .filter(Objects::nonNull)
                .forEach(cards::add);
        player.moveCardsToExile(cards.getCards(game), source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        player.shuffleLibrary(source, game);
        return true;
    }
}

class RoadsGoEverEverOnPutInHandEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    RoadsGoEverEverOnPutInHandEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Put a card exiled with {this} into its owner's hand.";
    }

    private RoadsGoEverEverOnPutInHandEffect(final RoadsGoEverEverOnPutInHandEffect effect) {
        super(effect);
    }

    @Override
    public RoadsGoEverEverOnPutInHandEffect copy() {
        return new RoadsGoEverEverOnPutInHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(filter, CardUtil.getExileZoneId(game, source));
        target.withNotTarget(true);
        controller.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && controller.moveCards(card, Zone.HAND, source, game);
    }
}
