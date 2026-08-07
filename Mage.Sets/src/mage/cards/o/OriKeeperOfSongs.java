package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.abilities.keyword.StoriedAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class OriKeeperOfSongs extends CardImpl {

    public OriKeeperOfSongs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Storied
        this.addAbility(new StoriedAbility());

        // As long as you have an enduring story, Ori gets +1/+0 and has vigilance.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
            EnduringStoryCondition.instance,
            "as long as you have an enduring story, {this} gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(VigilanceAbility.getInstance()),
            EnduringStoryCondition.instance,
            "and has vigilance"
        ));
        this.addAbility(ability.addHint(EnduringStoryHint.instance));
    }

    private OriKeeperOfSongs(final OriKeeperOfSongs card) {
        super(card);
    }

    @Override
    public OriKeeperOfSongs copy() {
        return new OriKeeperOfSongs(this);
    }
}
