package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuelcraftTrainer extends CardImpl {

    public DuelcraftTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Coven — At the beginning of combat on your turn, if you control three or more creatures with different powers, target creature you control gains double strike until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn)
        ).withInterveningIf(CovenCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private DuelcraftTrainer(final DuelcraftTrainer card) {
        super(card);
    }

    @Override
    public DuelcraftTrainer copy() {
        return new DuelcraftTrainer(this);
    }
}
