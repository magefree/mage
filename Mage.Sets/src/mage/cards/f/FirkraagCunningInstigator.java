package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirkraagCunningInstigator extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filterDragons = new FilterControlledCreaturePermanent("Dragons you control");

    static {
        filter.add(FirkraagCunningInstigatorPredicate.instance);
        filterDragons.add(SubType.DRAGON.getPredicate());
    }

    public FirkraagCunningInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more Dragons you control attack an opponent, goad target creature that player controls.
        Ability abilityGoad = new AttacksWithCreaturesTriggeredAbility(new GoadTargetEffect(), 1, filterDragons);
        abilityGoad.addTarget(new TargetPermanent(new FilterCreaturePermanent("target creature that player controls")));
        abilityGoad.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(abilityGoad);

        // Whenever a creature deals combat damage to one of your opponents, if that creature had to attack this combat, you put a +1/+1 counter on Firkraag, Cunning Instigator and you draw a card.
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("you put a +1/+1 counter on {this}"),
                filter, false, SetTargetPointer.NONE,
                true, false, TargetController.OPPONENT
        ).setTriggerPhrase("Whenever a creature deals combat damage to one of your opponents, " +
                "if that creature had to attack this combat, ");
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
        this.addAbility(ability);
    }

    private FirkraagCunningInstigator(final FirkraagCunningInstigator card) {
        super(card);
    }

    @Override
    public FirkraagCunningInstigator copy() {
        return new FirkraagCunningInstigator(this);
    }
}

enum FirkraagCunningInstigatorPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return game
                .getCombat()
                .getCreaturesForcedToAttack()
                .keySet()
                .stream()
                .anyMatch(input.getId()::equals);
    }
}
