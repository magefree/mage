package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class TenuredConcocter extends CardImpl {

    public TenuredConcocter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever this creature becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
            new DrawCardSourceControllerEffect(1),
            StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS,
            SetTargetPointer.NONE, true
        ));

        // Infusion -- This creature gets +2/+0 as long as you gained life this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
            YouGainedLifeCondition.getZero(), "{this} gets +2/+0 as long as you gained life this turn"
        )).setAbilityWord(AbilityWord.INFUSION).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private TenuredConcocter(final TenuredConcocter card) {
        super(card);
    }

    @Override
    public TenuredConcocter copy() {
        return new TenuredConcocter(this);
    }
}
