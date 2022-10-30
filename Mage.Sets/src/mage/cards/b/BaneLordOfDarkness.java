package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaneLordOfDarkness extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public BaneLordOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // As long as your life total is less than or equal to half your starting life total, Bane, Lord of Darkness has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield
                ), BaneLordOfDarknessCondition.instance, "as long as your life total is less than or equal " +
                "to half your starting life total, {this} has indestructible"
        )));

        // Whenever another nontoken creature you control dies, target opponent may have you draw a card. If they don't, you may put a creature card with equal or lesser toughness from your hand onto the battlefield.
        Ability ability = new DiesCreatureTriggeredAbility(new BaneLordOfDarknessEffect(), false, filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BaneLordOfDarkness(final BaneLordOfDarkness card) {
        super(card);
    }

    @Override
    public BaneLordOfDarkness copy() {
        return new BaneLordOfDarkness(this);
    }
}

enum BaneLordOfDarknessCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .filter(Objects::nonNull)
                .map(Player::getLife)
                .map(x -> 2 * x <= game.getStartingLife())
                .orElse(false);
    }
}

class BaneLordOfDarknessEffect extends OneShotEffect {

    BaneLordOfDarknessEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent may have you draw a card. If they don't, " +
                "you may put a creature card with equal or lesser toughness from your hand onto the battlefield";
    }

    private BaneLordOfDarknessEffect(final BaneLordOfDarknessEffect effect) {
        super(effect);
    }

    @Override
    public BaneLordOfDarknessEffect copy() {
        return new BaneLordOfDarknessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        if (opponent.chooseUse(
                outcome, "Have " + controller.getName() + " draw a card or put a creature into play?",
                null, "Draw card", "Play creature", source, game
        )) {
            return controller.drawCards(1, source, game) > 0;
        }
        Permanent permanent = (Permanent) getValue("creatureDied");
        if (permanent == null) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard(
                "creature card with " + permanent.getToughness().getValue() + " or less toughness"
        );
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, permanent.getToughness().getValue() + 1));
        return new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source);
    }
}
// nobody cared who I was until I put on the mask
