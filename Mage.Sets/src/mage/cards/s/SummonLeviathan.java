package mage.cards.s;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonLeviathan extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.KRAKEN.getPredicate()));
        filter.add(Predicates.not(SubType.LEVIATHAN.getPredicate()));
        filter.add(Predicates.not(SubType.MERFOLK.getPredicate()));
        filter.add(Predicates.not(SubType.OCTOPUS.getPredicate()));
        filter.add(Predicates.not(SubType.SERPENT.getPredicate()));
    }

    public SummonLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Return each creature that isn't a Kraken, Leviathan, Merfolk, Octopus, or Serpent to its owner's hand.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new ReturnToHandFromBattlefieldAllEffect(filter)
                        .setText("return each creature that isn't a Kraken, Leviathan, Merfolk, Octopus, or Serpent to its owner's hand")
        );

        // II, III -- Until end of turn, whenever a Kraken, Leviathan, Merfolk, Octopus, or Serpent attacks, draw a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new SummonLeviathanTriggeredAbility())
        );
        this.addAbility(sagaAbility);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));
    }

    private SummonLeviathan(final SummonLeviathan card) {
        super(card);
    }

    @Override
    public SummonLeviathan copy() {
        return new SummonLeviathan(this);
    }
}

class SummonLeviathanTriggeredAbility extends DelayedTriggeredAbility {

    SummonLeviathanTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false, false);
        this.setTriggerPhrase("Until end of turn, whenever a Kraken, Leviathan, Merfolk, Octopus, or Serpent attacks, ");
    }

    private SummonLeviathanTriggeredAbility(final SummonLeviathanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SummonLeviathanTriggeredAbility copy() {
        return new SummonLeviathanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && (permanent.hasSubtype(SubType.KRAKEN, game)
                || permanent.hasSubtype(SubType.LEVIATHAN, game)
                || permanent.hasSubtype(SubType.MERFOLK, game)
                || permanent.hasSubtype(SubType.OCTOPUS, game)
                || permanent.hasSubtype(SubType.SERPENT, game));
    }
}
