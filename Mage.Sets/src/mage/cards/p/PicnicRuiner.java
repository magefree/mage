package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PicnicRuiner extends AdventureCard {

    public PicnicRuiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{R}", "Stolen Goodies", "{3}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Picnic Ruiner attacks while you control a creature with power 4 or greater, Picnic Ruiner gains double strike until end of turn.
        this.addAbility(new ConditionalTriggeredAbility(
                new AttacksTriggeredAbility(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), false),
                FerociousCondition.instance,
                "Whenever {this} attacks while you control a creature with power 4 or greater, {this} gains double strike until end of turn."
        ));

        // Stolen Goodies
        // Distribute three +1/+1 counters among any number of target creatures you control.
        this.getSpellCard().getSpellAbility().addEffect(
                new DistributeCountersEffect(
                        CounterType.P1P1, 3, false,
                        "any number of target creatures you control"
                )
        );
        this.getSpellCard().getSpellAbility().addTarget(
                new TargetCreaturePermanentAmount(3, StaticFilters.FILTER_CONTROLLED_CREATURES)
        );

        this.finalizeAdventure();
    }

    private PicnicRuiner(final PicnicRuiner card) {
        super(card);
    }

    @Override
    public PicnicRuiner copy() {
        return new PicnicRuiner(this);
    }
}
