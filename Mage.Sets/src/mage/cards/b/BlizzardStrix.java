package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlizzardStrix extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");
    private static final FilterPermanent filter2 = new FilterPermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
        filter2.add(SuperType.SNOW.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter2);

    public BlizzardStrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Blizzard Strix enters the battlefield, if you control another snow permanent, exile target permanent other than Blizzard Strix. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BlizzardStrixEffect()), condition,
                "When {this} enters the battlefield, if you control another snow permanent, " +
                        "exile target permanent other than {this}. Return that card to the battlefield " +
                        "under its owner's control at the beginning of the next end step."
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BlizzardStrix(final BlizzardStrix card) {
        super(card);
    }

    @Override
    public BlizzardStrix copy() {
        return new BlizzardStrix(this);
    }
}

class BlizzardStrixEffect extends OneShotEffect {

    BlizzardStrixEffect() {
        super(Outcome.Detriment);
    }

    private BlizzardStrixEffect(final BlizzardStrixEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || permanent == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        //create delayed triggered ability
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false)
                        .setTargetPointer(new FixedTarget(source.getFirstTarget(), game))
        ), source);
        return true;
    }

    @Override
    public BlizzardStrixEffect copy() {
        return new BlizzardStrixEffect(this);
    }
}
