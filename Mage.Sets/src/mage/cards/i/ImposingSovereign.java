package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ImposingSovereign extends CardImpl {

    public ImposingSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN, SubType.NOBLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("creatures your opponents control enter the battlefield tapped")));
    }

    private ImposingSovereign(final ImposingSovereign card) {
        super(card);
    }

    @Override
    public ImposingSovereign copy() {
        return new ImposingSovereign(this);
    }
}
