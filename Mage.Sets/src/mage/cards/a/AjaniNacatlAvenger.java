package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CatWarrior21Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class AjaniNacatlAvenger extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.CAT, "Cat you control");

    public AjaniNacatlAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(3);

        this.color.setRed(true);
        this.color.setWhite(true);
        this.nightCard = true;

        // +2: Put a +1/+1 counter on each Cat you control.
        this.addAbility(new LoyaltyAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), 2
        ));

        // 0: Create a 2/1 white Car Warrior creature token. When you do, if you control a red permanent other than Ajani, Nacatl Avenger, he deals damage equal to the number of creatures you control to any target.
        this.addAbility(new LoyaltyAbility(new AjaniNacatlAvengerZeroEffect(), 0));

        // -4: Each opponent chooses an artifact, a creature, an enchantment and a planeswalker from among the nonland permanents they control, then sacrifices the rest.
        this.addAbility(new LoyaltyAbility(new AjaniNacatlAvengerMinusFourEffect(), -4));
    }

    private AjaniNacatlAvenger(final AjaniNacatlAvenger card) {
        super(card);
    }

    @Override
    public AjaniNacatlAvenger copy() {
        return new AjaniNacatlAvenger(this);
    }
}

class AjaniNacatlAvengerZeroEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("red permanent other than {this}");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, true);

    AjaniNacatlAvengerZeroEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create a 2/1 white Cat Warrior creature token. "
                + "When you do, if you control a red permanent other than {this}, "
                + "he deals damage equal to the number of creatures you control to any target.";
    }

    private AjaniNacatlAvengerZeroEffect(final AjaniNacatlAvengerZeroEffect effect) {
        super(effect);
    }

    @Override
    public AjaniNacatlAvengerZeroEffect copy() {
        return new AjaniNacatlAvengerZeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!new CreateTokenEffect(new CatWarrior21Token()).apply(game, source)) {
            return false;
        }

        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(CreaturesYouControlCount.instance),
                false,
                "When you do, if you control a red permanent other than {this}, "
                        + "he deals damage equal to the number of creatures you control to any target.",
                condition
        );
        reflexive.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }
}

// Inspired by Mythos of Snapdax
class AjaniNacatlAvengerMinusFourEffect extends OneShotEffect {

    private static final List<CardType> cardTypes = Arrays.asList(
            CardType.ARTIFACT,
            CardType.CREATURE,
            CardType.ENCHANTMENT,
            CardType.PLANESWALKER
    );

    AjaniNacatlAvengerMinusFourEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent chooses an artifact, a creature, an enchantment and a planeswalker "
                + "from among the nonland permanents they control, then sacrifices the rest.";
    }

    private AjaniNacatlAvengerMinusFourEffect(final AjaniNacatlAvengerMinusFourEffect effect) {
        super(effect);
    }

    @Override
    public AjaniNacatlAvengerMinusFourEffect copy() {
        return new AjaniNacatlAvengerMinusFourEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        List<Player> playerList = game
                .getOpponents(controller.getId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Set<UUID> toKeep = new HashSet<>();
        for (Player player : playerList) {
            for (CardType cardType : cardTypes) {
                String message = CardUtil.addArticle(cardType.toString());
                FilterPermanent filter = new FilterNonlandPermanent(message);
                filter.add(cardType.getPredicate());
                filter.add(new ControllerIdPredicate(player.getId()));
                if (game.getBattlefield().count(filter, source.getControllerId(), source, game) == 0) {
                    continue;
                }
                TargetPermanent target = new TargetPermanent(filter);
                target.withNotTarget(true);
                player.choose(outcome, target, source, game);
                toKeep.add(target.getFirstTarget());
            }
        }

        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_NON_LAND, source.getControllerId(), game)) {
            if (permanent == null || toKeep.contains(permanent.getId()) || !controller.hasOpponent(permanent.getControllerId(), game)) {
                continue;
            }
            permanent.sacrifice(source, game);
        }
        return true;
    }

}
