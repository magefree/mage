package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DaringSleuth extends CardImpl {

    public DaringSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.b.BearerOfOverwhelmingTruths.class;

        // When you sacrifice a Clue, transform Daring Sleuth.
        this.addAbility(new TransformAbility());
        this.addAbility(new SacrificePermanentTriggeredAbility(new TransformSourceEffect(), StaticFilters.FILTER_CONTROLLED_CLUE)
                .setTriggerPhrase("When you sacrifice a Clue, "));
    }

    private DaringSleuth(final DaringSleuth card) {
        super(card);
    }

    @Override
    public DaringSleuth copy() {
        return new DaringSleuth(this);
    }
}
