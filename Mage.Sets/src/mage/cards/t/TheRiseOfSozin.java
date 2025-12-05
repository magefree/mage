package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRiseOfSozin extends TransformingDoubleFacedCard {

    public TheRiseOfSozin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{4}{B}{B}",
                "Fire Lord Sozin",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE}, "B"
        );

        // The Rise of Sozin
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I -- Destroy all creatures.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)
        );

        // II -- Choose a card name. Search target opponent's graveyard, hand, and library for up to four cards with that name and exile them. Then that player shuffles.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II,
                new Effects(
                        new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL), new TheRiseOfSozinEffect()
                ), new TargetOpponent()
        );

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Fire Lord Sozin
        this.getRightHalfCard().setPT(5, 5);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Firebending 3
        this.getRightHalfCard().addAbility(new FirebendingAbility(3));

        // Whenever Fire Lord Sozin deals combat damage to a player, you may pay {X}. When you do, put any number of target creature cards with total mana value X or less from that player's graveyard onto the battlefield under your control.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new FireLordSozinEffect()));
    }

    private TheRiseOfSozin(final TheRiseOfSozin card) {
        super(card);
    }

    @Override
    public TheRiseOfSozin copy() {
        return new TheRiseOfSozin(this);
    }
}

class TheRiseOfSozinEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    TheRiseOfSozinEffect() {
        super(true, "target opponent's", "up to four cards with that name", false, 4);
    }

    private TheRiseOfSozinEffect(final TheRiseOfSozinEffect effect) {
        super(effect);
    }

    @Override
    public TheRiseOfSozinEffect copy() {
        return new TheRiseOfSozinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String chosenCardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return applySearchAndExile(game, source, chosenCardName, getTargetPointer().getFirst(game, source));
    }
}

class FireLordSozinEffect extends OneShotEffect {

    FireLordSozinEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. When you do, put any number of target creature cards with " +
                "total mana value X or less from that player's graveyard onto the battlefield under your control";
    }

    private FireLordSozinEffect(final FireLordSozinEffect effect) {
        super(effect);
    }

    @Override
    public FireLordSozinEffect copy() {
        return new FireLordSozinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (!controller.chooseUse(Outcome.BoostCreature, "Pay {X}?", source, game)) {
            return false;
        }
        int xValue = controller.announceX(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source, true);
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        cost.add(new GenericManaCost(xValue));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new FireLordSozinTarget((UUID) getValue("damagedPlayer"), xValue));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class FireLordSozinTarget extends TargetCardInGraveyard {

    private final int xValue;

    private static final FilterCard makeFilter(UUID ownerId, int xValue) {
        FilterCard filter = new FilterCreatureCard("creature cards with total mana value " + xValue + " or less from that player's graveyard");
        filter.add(new OwnerIdPredicate(ownerId));
        return filter;
    }

    FireLordSozinTarget(UUID ownerId, int xValue) {
        super(0, Integer.MAX_VALUE, makeFilter(ownerId, xValue), false);
        this.xValue = xValue;
    }

    private FireLordSozinTarget(final FireLordSozinTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public FireLordSozinTarget copy() {
        return new FireLordSozinTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(this.getTargets(), id, MageObject::getManaValue, xValue, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(
                this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, xValue, game
        );
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
