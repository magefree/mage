package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class ResplendentMarshal extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("another creature card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ResplendentMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Resplendent Marshal enters the battlefield or dies, you may exile another creature card from your graveyard.
        // When you do, put a +1/+1 counter on each creature you control other than Resplendent Marshal that shares a creature type with the exiled card.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new DoWhenCostPaid(
                        new ReflexiveTriggeredAbility(new ResplendentMarshalEffect(), false,
                                "put a +1/+1 counter on each creature you control other than Resplendent Marshal that shares a creature type with the exiled card"),
                        new ExileFromGraveCost(new TargetCardInYourGraveyard(filter), true),
                        "Exile another creature card from your graveyard?"
                ), false
        ));
    }

    private ResplendentMarshal(final ResplendentMarshal card) {
        super(card);
    }

    @Override
    public ResplendentMarshal copy() {
        return new ResplendentMarshal(this);
    }
}

class ResplendentMarshalEffect extends OneShotEffect {

    public ResplendentMarshalEffect() {
        super(Outcome.Benefit);
    }

    private ResplendentMarshalEffect (final ResplendentMarshalEffect effect) {
        super(effect);
    }

    @Override
    public ResplendentMarshalEffect copy() {
        return new ResplendentMarshalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        Card exiledCard = game.getCard(targetPointer.getFirst(game, source));
        if (controller != null && sourceObject != null && exiledCard != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, source.getControllerId(), source, game)) {
                if (permanent.shareCreatureTypes(game, exiledCard)) {
                    permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName()
                                + " puts a +1/+1 counter on " + permanent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
