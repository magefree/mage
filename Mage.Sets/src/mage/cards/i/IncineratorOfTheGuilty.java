package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.costs.common.CollectEvidenceXCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author DominionSpy
 */
public final class IncineratorOfTheGuilty extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature and each planeswalker");

    static {
        filter.add(
                Predicates.or(
                        CardType.CREATURE.getPredicate(),
                        CardType.PLANESWALKER.getPredicate()));
    }

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
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new DamageAllControlledTargetEffect(GetXValue.instance, filter), false);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoWhenCostPaid(reflexive, new CollectEvidenceXCost(), "Collect evidence X to deal damage?"),
                false, true);
        this.addAbility(ability);
    }

    private IncineratorOfTheGuilty(final IncineratorOfTheGuilty card) {
        super(card);
    }

    @Override
    public IncineratorOfTheGuilty copy() {
        return new IncineratorOfTheGuilty(this);
    }
}
