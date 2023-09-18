package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.HumanToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnhartMentor extends CardImpl {

    public DawnhartMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // When Dawnhart Mentor enters the battlefield, create a 1/1 white Human creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanToken())));

        // Coven — {5}{G}: Target creature you control gets +3/+3 and gains trample until end of turn. Activate only if you control three or more creatures with different powers.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new BoostTargetEffect(3, 3)
                .setText("target creature you control gets +3/+3"),
                new ManaCostsImpl<>("{5}{G}"), CovenCondition.instance
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private DawnhartMentor(final DawnhartMentor card) {
        super(card);
    }

    @Override
    public DawnhartMentor copy() {
        return new DawnhartMentor(this);
    }
}
