package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class Wargling extends CardImpl {

    public Wargling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ferocious -- Whenever this creature attacks while you control a creature with power 4 or greater, until end of turn, this creature gets +1/+0 and creatures you control gain trample.
        Ability ability = new AttacksTriggeredAbility(
            new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("until end of turn, this creature gets +1/+0")
        ).withTriggerCondition(FerociousCondition.instance)
            .setAbilityWord(AbilityWord.FEROCIOUS)
            .addHint(FerociousHint.instance);
        ability.addEffect(
            new GainAbilityAllEffect(TrampleAbility.getInstance(),
            Duration.EndOfTurn,
            new FilterControlledCreaturePermanent(),
            "and creatures you control gain trample")
        );
        this.addAbility(ability);
    }

    private Wargling(final Wargling card) {
        super(card);
    }

    @Override
    public Wargling copy() {
        return new Wargling(this);
    }
}
