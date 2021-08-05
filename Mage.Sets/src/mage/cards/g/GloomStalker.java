package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloomStalker extends CardImpl {

    public GloomStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As long as you've completed a dungeon, Gloom Stalker has double strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield
                ), CompletedDungeonCondition.instance,
                "as long as you've completed a dungeon, {this} has double strike"
        )).addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private GloomStalker(final GloomStalker card) {
        super(card);
    }

    @Override
    public GloomStalker copy() {
        return new GloomStalker(this);
    }
}
