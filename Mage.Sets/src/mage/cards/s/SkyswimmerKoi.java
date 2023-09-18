package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyswimmerKoi extends CardImpl {

    public SkyswimmerKoi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an artifact enters the battlefield under your control, you may draw a card. If you do, discard a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        ));
    }

    private SkyswimmerKoi(final SkyswimmerKoi card) {
        super(card);
    }

    @Override
    public SkyswimmerKoi copy() {
        return new SkyswimmerKoi(this);
    }
}
