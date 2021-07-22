package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author fireshoes
 */
public final class ObsessiveSkinner extends CardImpl {

    public ObsessiveSkinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Obsessive Skinner enters the battlefield, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // <i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard,
        // put a +1/+1 counter on target creature.
        ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.OPPONENT, false),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard, "
                        + "put a +1/+1 counter on target creature.");
        ability.addTarget(new TargetCreaturePermanent());
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private ObsessiveSkinner(final ObsessiveSkinner card) {
        super(card);
    }

    @Override
    public ObsessiveSkinner copy() {
        return new ObsessiveSkinner(this);
    }
}
