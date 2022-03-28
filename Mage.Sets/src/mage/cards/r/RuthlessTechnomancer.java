package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuthlessTechnomancer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("artifacts");

    public RuthlessTechnomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Ruthless Technomancer enters the battlefield, you may sacrifice another creature you control. If you do, create a number of Treasure tokens equal to that creature's power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RuthlessTechnomancerEffect()));

        // {2}{B}, Sacrifice X artifacts: Return target creature card with power X or less from your graveyard to the battlefield. X can't be 0.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("return target creature card with power X or less " +
                                "from your graveyard to the battlefield. X can't be 0"),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new SacrificeXTargetCost(filter, false, 1));
        this.addAbility(ability.setTargetAdjuster(RuthlessTechnomancerAdjuster.instance));
    }

    private RuthlessTechnomancer(final RuthlessTechnomancer card) {
        super(card);
    }

    @Override
    public RuthlessTechnomancer copy() {
        return new RuthlessTechnomancer(this);
    }
}

enum RuthlessTechnomancerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = GetXValue.instance.calculate(game, ability, null);
        FilterCard filter = new FilterCreatureCard(
                "creature card in your graveyard with mana value " + xValue + " or less"
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}

class RuthlessTechnomancerEffect extends OneShotEffect {

    RuthlessTechnomancerEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature you control. If you do, " +
                "create a number of Treasure tokens equal to that creature's power";
    }

    private RuthlessTechnomancerEffect(final RuthlessTechnomancerEffect effect) {
        super(effect);
    }

    @Override
    public RuthlessTechnomancerEffect copy() {
        return new RuthlessTechnomancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        );
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        int power = permanent.getPower().getValue();
        return power < 1 || new TreasureToken().putOntoBattlefield(power, game, source);
    }
}
