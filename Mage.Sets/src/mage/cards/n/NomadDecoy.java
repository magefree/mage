package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class NomadDecoy extends CardImpl {

    public NomadDecoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W}, {tap}: Tap target creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Threshold - {W}{W}, {T}: Tap two target creatures. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ActivateIfConditionActivatedAbility(
                new TapTargetEffect(), new ManaCostsImpl<>("{W}{W}"), ThresholdCondition.instance
        );
        thresholdAbility.addCost(new TapSourceCost());
        thresholdAbility.addTarget(new TargetCreaturePermanent(2));
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private NomadDecoy(final NomadDecoy card) {
        super(card);
    }

    @Override
    public NomadDecoy copy() {
        return new NomadDecoy(this);
    }
}
