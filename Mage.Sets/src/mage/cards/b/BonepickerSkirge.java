package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BonepickerSkirge extends CardImpl {

    public BonepickerSkirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Corrupted -- As long as an opponent has three or more poison counters, Bonepicker Skirge has deathtouch and lifelink.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()), CorruptedCondition.instance,
                "as long as an opponent has three or more poison counters, {this} has deathtouch"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()),
                CorruptedCondition.instance, "and lifelink"
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private BonepickerSkirge(final BonepickerSkirge card) {
        super(card);
    }

    @Override
    public BonepickerSkirge copy() {
        return new BonepickerSkirge(this);
    }
}
