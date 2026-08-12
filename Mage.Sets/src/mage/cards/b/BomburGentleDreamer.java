package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.abilities.keyword.StoriedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author muz
 */
public final class BomburGentleDreamer extends CardImpl {

    public BomburGentleDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Storied
        this.addAbility(new StoriedAbility());

        // Bombur doesn't untap during your untap step unless you have an enduring story.
        ContinuousRuleModifyingEffect effect = new DontUntapInControllersUntapStepSourceEffect(false, true);
        effect.setText("{this} doesn't untap during your untap step unless you have an enduring story");
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(
            effect, new InvertCondition(EnduringStoryCondition.instance)
        )).addHint(EnduringStoryHint.instance));
    }

    private BomburGentleDreamer(final BomburGentleDreamer card) {
        super(card);
    }

    @Override
    public BomburGentleDreamer copy() {
        return new BomburGentleDreamer(this);
    }
}
