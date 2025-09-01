package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StampedingElkHerd extends CardImpl {

    public StampedingElkHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // <i>Formidable</i> &mdash; Whenever Stampeding Elk Herd attacks, if creatures you control have total power 8 or greater, creatures you control gain trample until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES
        )).withInterveningIf(FormidableCondition.instance).setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private StampedingElkHerd(final StampedingElkHerd card) {
        super(card);
    }

    @Override
    public StampedingElkHerd copy() {
        return new StampedingElkHerd(this);
    }
}
