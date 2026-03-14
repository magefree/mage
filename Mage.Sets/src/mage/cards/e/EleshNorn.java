package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SourceDealsDamageToYouTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EleshNorn extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("other creatures");
    private static final FilterPermanent argentFilter = new FilterPermanent("other permanents except for artifacts, lands, and Phyrexians");

    static {
        filter.add(AnotherPredicate.instance);
        argentFilter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        argentFilter.add(Predicates.not(CardType.LAND.getPredicate()));
        argentFilter.add(Predicates.not(SubType.PHYREXIAN.getPredicate()));
        argentFilter.add(AnotherPredicate.instance);
    }

    public EleshNorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.PRAETOR}, "{2}{W}{W}",
                "The Argent Etchings",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "W"
        );

        // Elesh Norn
        this.getLeftHalfCard().setPT(3, 5);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Whenever a source an opponent controls deals damage to you or a permanent you control, that source's controller loses 2 life unless they pay {1}.
        this.getLeftHalfCard().addAbility(new SourceDealsDamageToYouTriggeredAbility(new EleshNornEffect(), StaticFilters.FILTER_PERMANENT, false));

        // {2}{W}, Sacrifice three other creatures: Exile Elesh Norn, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new SacrificeTargetCost(3, filter));
        this.getLeftHalfCard().addAbility(ability);

        // The Argent Etchings

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I -- Incubate 2 five times, then transform all Incubator tokens you control.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, new TheArgentEtchingsEffect());

        // II -- Creatures you control get +1/+1 and gain double strike until end of turn.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_II,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1"),
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("and gain double strike until end of turn")
        );

        // III -- Destroy all other permanents except for artifacts, lands, and Phyrexians. Exile The Argent Etchings, then return it to the battlefield.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_III,
                new DestroyAllEffect(argentFilter),
                new ExileSourceAndReturnFaceUpEffect()
        );
        this.getRightHalfCard().addAbility(sagaAbility);
    }

    private EleshNorn(final EleshNorn card) {
        super(card);
    }

    @Override
    public EleshNorn copy() {
        return new EleshNorn(this);
    }
}

class EleshNornEffect extends OneShotEffect {

    EleshNornEffect() {
        super(Outcome.Benefit);
        staticText = "that source's controller loses 2 life unless they pay {1}";
    }

    private EleshNornEffect(final EleshNornEffect effect) {
        super(effect);
    }

    @Override
    public EleshNornEffect copy() {
        return new EleshNornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cost cost = new GenericManaCost(1);
        if (cost.canPay(source, source, player.getId(), game)
                && player.chooseUse(Outcome.PreventDamage, "Pay {1}?", source, game)
                && cost.pay(source, game, source, player.getId(), false)) {
            return true;
        }
        return player.loseLife(2, game, source, false) > 0;
    }
}

class TheArgentEtchingsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.INCUBATOR);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    TheArgentEtchingsEffect() {
        super(Outcome.Benefit);
        staticText = "incubate 2 five times, then transform all Incubator tokens you control";
    }

    private TheArgentEtchingsEffect(final TheArgentEtchingsEffect effect) {
        super(effect);
    }

    @Override
    public TheArgentEtchingsEffect copy() {
        return new TheArgentEtchingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < 5; i++) {
            IncubateEffect.doIncubate(2, game, source);
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            permanent.transform(source, game);
        }
        return true;
    }
}
