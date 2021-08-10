package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.hint.common.MorbidHint;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class GrimWanderer extends CardImpl {

    public GrimWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Tragic Backstory — Cast this spell only if a creature died this turn.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(MorbidCondition.instance).withFlavorWord("Tragic Backstory").addHint(MorbidHint.instance));
    }

    private GrimWanderer(final GrimWanderer card) {
        super(card);
    }

    @Override
    public GrimWanderer copy() {
        return new GrimWanderer(this);
    }
}
