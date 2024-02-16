package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author North, nantuko
 */
public final class SejiriMerfolk extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.PLAINS);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public SejiriMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()), condition,
                "as long as you control a Plains, {this} has first strike"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance()), condition, "and lifelink"
        ));
        this.addAbility(ability);
    }

    private SejiriMerfolk(final SejiriMerfolk card) {
        super(card);
    }

    @Override
    public SejiriMerfolk copy() {
        return new SejiriMerfolk(this);
    }
}
