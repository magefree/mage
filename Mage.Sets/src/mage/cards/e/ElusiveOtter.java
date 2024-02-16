package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesWithLessPowerEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ElusiveOtter extends AdventureCard {

    public ElusiveOtter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{U}", "Grove's Bounty", "{X}{G}");

        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Creatures with power less than Elusive Otter's power can't block it.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesWithLessPowerEffect()));

        // Grove's Bounty
        // Distribute X +1/+1 counters among any number of target creatures you control.
        this.getSpellCard().getSpellAbility().addEffect(new DistributeCountersEffect(
                CounterType.P1P1, ManacostVariableValue.REGULAR, false,
                "any number of target creatures you control"
        ));
        Target target = new TargetCreaturePermanentAmount(ManacostVariableValue.REGULAR, StaticFilters.FILTER_CONTROLLED_CREATURES);
        target.setMinNumberOfTargets(0);
        target.setMaxNumberOfTargets(Integer.MAX_VALUE);
        this.getSpellCard().getSpellAbility().addTarget(target);

        this.finalizeAdventure();
    }

    private ElusiveOtter(final ElusiveOtter card) {
        super(card);
    }

    @Override
    public ElusiveOtter copy() {
        return new ElusiveOtter(this);
    }
}
