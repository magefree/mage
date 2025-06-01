package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
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
public final class RookTurret extends CardImpl {

    public RookTurret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another artifact you control enters, you may draw a card. If you do, discard a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DrawDiscardControllerEffect(true), StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT
        ));
    }

    private RookTurret(final RookTurret card) {
        super(card);
    }

    @Override
    public RookTurret copy() {
        return new RookTurret(this);
    }
}
