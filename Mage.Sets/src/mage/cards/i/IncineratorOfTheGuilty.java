package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author DominionSpy
 */
public final class IncineratorOfTheGuilty extends CardImpl {

    public IncineratorOfTheGuilty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Incinerator of the Guilty deals combat damage to a player, you may collect evidence X.
        // When you do, Incinerator of the Guilty deals X damage to each creature and each planeswalker that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new IncineratorOfTheGuiltyEffect(), false, true));
    }

    private IncineratorOfTheGuilty(final IncineratorOfTheGuilty card) {
        super(card);
    }

    @Override
    public IncineratorOfTheGuilty copy() {
        return new IncineratorOfTheGuilty(this);
    }
}

class IncineratorOfTheGuiltyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("creature and each planeswalker");

    static {
        filter.add(
                Predicates.or(
                        CardType.CREATURE.getPredicate(),
                        CardType.PLANESWALKER.getPredicate()));
    }

    IncineratorOfTheGuiltyEffect() {
        super(Outcome.Benefit);
        staticText = "you may collect evidence X. When you do, {this} deals X damage " +
                "to each creature and each planeswalker that player controls.";
    }

    private IncineratorOfTheGuiltyEffect(final IncineratorOfTheGuiltyEffect effect) {
        super(effect);
    }

    @Override
    public IncineratorOfTheGuiltyEffect copy() {
        return new IncineratorOfTheGuiltyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(outcome, "Collect evidence X?", source, game)) {
            return false;
        }

        int xValue = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for X", game, source);
        CollectEvidenceCost cost = new CollectEvidenceCost(xValue);
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageAllControlledTargetEffect(xValue, filter)
                        .setTargetPointer(getTargetPointer().copy()), false);
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
