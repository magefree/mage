package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class PadishahEmperorShaddamIV extends CardImpl {

    private static final FilterCreaturePermanent filterNonToken = new FilterCreaturePermanent("another nontoken creature");
    private static final FilterCreaturePermanent filterGreatest
            = new FilterOpponentsCreaturePermanent("creature with the greatest power among creatures your opponents control");

    static {
        filterNonToken.add(AnotherPredicate.instance);
        filterNonToken.add(TokenPredicate.FALSE);
        filterGreatest.add(PadishahEmperorShaddamIVGreatestPowerPredicate.instance);
    }

    public PadishahEmperorShaddamIV(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Padishah Emperor Shaddam IV enters, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new BecomesMonarchSourceEffect()
        ).addHint(MonarchHint.instance));

        // Whenever another nontoken creature dies while you're the monarch, you may pay 1 life. If you do, create a Treasure token.
        this.addAbility(
            new DiesCreatureTriggeredAbility(
                new DoIfCostPaid(
                    new CreateTokenEffect(new TreasureToken()), new PayLifeCost(1)
                ),
                false, filterNonToken
            ).withTriggerCondition(MonarchIsSourceControllerCondition.instance)
            .setTriggerPhrase("Whenever another nontoken creature dies while you're the monarch, ")
        );

        // At the beginning of your end step, destroy target creature with the greatest power among creatures your opponents control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filterGreatest));
        this.addAbility(ability);
    }

    private PadishahEmperorShaddamIV(final PadishahEmperorShaddamIV card) {
        super(card);
    }

    @Override
    public PadishahEmperorShaddamIV copy() {
        return new PadishahEmperorShaddamIV(this);
    }
}

enum PadishahEmperorShaddamIVGreatestPowerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    private static final GreatestAmongPermanentsValue greatestPower = new GreatestAmongPermanentsValue(
        GreatestAmongPermanentsValue.Quality.Power, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES
    );

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getPower().getValue() >= greatestPower.calculate(game, input.getSource(), null);
    }
}
