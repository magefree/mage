package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SurrakTheHuntCaller extends CardImpl {

    public SurrakTheHuntCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // <i>Formidable</i> &mdash; At the beginning of combat on your turn, if creatures you control have total power 8 or greater, target creature you control gains haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        )).withInterveningIf(FormidableCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private SurrakTheHuntCaller(final SurrakTheHuntCaller card) {
        super(card);
    }

    @Override
    public SurrakTheHuntCaller copy() {
        return new SurrakTheHuntCaller(this);
    }
}
