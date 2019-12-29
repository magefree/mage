package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.DeliriumHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class ThrabenFoulbloods extends CardImpl {

    public ThrabenFoulbloods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HOUND);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Delirium</i> &mdash; Thraben Foulbloods gets +1/+1 and has menace as long as there are four or more card types among cards in your graveyard. <i>(A creature with menace can't be blocked except by two or more creatures.)<i>
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), DeliriumCondition.instance, "<i>Delirium</i> &mdash; {this} gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(new MenaceAbility()), DeliriumCondition.instance,
                "and has menace as long as there are four or more card types among cards in your graveyard.  <i>(A creature with menace can't be blocked except by two or more creatures.)</i>"));
        ability.addHint(DeliriumHint.instance);
        this.addAbility(ability);
    }

    public ThrabenFoulbloods(final ThrabenFoulbloods card) {
        super(card);
    }

    @Override
    public ThrabenFoulbloods copy() {
        return new ThrabenFoulbloods(this);
    }
}
