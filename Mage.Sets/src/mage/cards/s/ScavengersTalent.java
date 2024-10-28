package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScavengersTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("other nonland permanents");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public ScavengersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever one or more creatures you control die, create a Food token. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new FoodToken()), false,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setTriggersLimitEachTurn(1).setTriggerPhrase("Whenever one or more creatures you control die, "));

        // {1}{B}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{B}"));

        // Whenever you sacrifice a permanent, target player mills two cards.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new MillCardsTargetEffect(2), StaticFilters.FILTER_PERMANENT_A
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {2}{B}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{2}{B}"));

        // At the beginning of your end step, you may sacrifice three other nonland permanents. If you do, return a creature card from your graveyard to the battlefield with a finality counter on it.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new BeginningOfEndStepTriggeredAbility(
                        new DoIfCostPaid(
                                new ScavengersTalentEffect(), new SacrificeTargetCost(3, filter)
                        )
                ), 3
        )));
    }

    private ScavengersTalent(final ScavengersTalent card) {
        super(card);
    }

    @Override
    public ScavengersTalent copy() {
        return new ScavengersTalent(this);
    }
}

class ScavengersTalentEffect extends OneShotEffect {

    ScavengersTalentEffect() {
        super(Outcome.Benefit);
        staticText = "return a creature card from your graveyard to the battlefield with a finality counter on it";
    }

    private ScavengersTalentEffect(final ScavengersTalentEffect effect) {
        super(effect);
    }

    @Override
    public ScavengersTalentEffect copy() {
        return new ScavengersTalentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        return new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance())
                .setTargetPointer(new FixedTarget(target.getFirstTarget(), game))
                .apply(game, source);
    }
}
