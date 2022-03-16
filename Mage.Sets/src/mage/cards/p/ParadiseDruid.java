package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParadiseDruid extends CardImpl {

    public ParadiseDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Paradise Druid has hexproof as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        HexproofAbility.getInstance(),
                        Duration.WhileOnBattlefield
                ), SourceTappedCondition.UNTAPPED,
                "{this} has hexproof as long as it's untapped"
        )));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ParadiseDruid(final ParadiseDruid card) {
        super(card);
    }

    @Override
    public ParadiseDruid copy() {
        return new ParadiseDruid(this);
    }
}
