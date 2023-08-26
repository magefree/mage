package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GallantPieWielder extends CardImpl {

    public GallantPieWielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Celebration -- Gallant Pie-Wielder has double strike as long as two or more nonland permanents entered the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance()),
                CelebrationCondition.instance, "{this} has double strike as long as two or " +
                "more nonland permanents entered the battlefield under your control this turn"
        )).addHint(CelebrationCondition.getHint()).setAbilityWord(AbilityWord.CELEBRATION), new CelebrationWatcher());
    }

    private GallantPieWielder(final GallantPieWielder card) {
        super(card);
    }

    @Override
    public GallantPieWielder copy() {
        return new GallantPieWielder(this);
    }
}
