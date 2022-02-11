package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BereavedSurvivor extends CardImpl {

    public BereavedSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.d.DauntlessAvenger.class;

        // When another creature you control dies, transform Bereaved Survivor.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesCreatureTriggeredAbility(
                new TransformSourceEffect(), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setTriggerPhrase("When another creature you control dies, "));
    }

    private BereavedSurvivor(final BereavedSurvivor card) {
        super(card);
    }

    @Override
    public BereavedSurvivor copy() {
        return new BereavedSurvivor(this);
    }
}
