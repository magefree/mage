package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class YaroksWavecrasher extends CardImpl {

    public YaroksWavecrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Yarok’s Wavecrasher enters the battlefield, return another creature you control to its owner’s hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OneShotNonTargetEffect(
                new ReturnToHandTargetEffect().setText("return another creature you control to its owner's hand"),
                new TargetControlledPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL))));
    }

    private YaroksWavecrasher(final YaroksWavecrasher card) {
        super(card);
    }

    @Override
    public YaroksWavecrasher copy() {
        return new YaroksWavecrasher(this);
    }
}
