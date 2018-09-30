package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class GolgariFindbroker extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card from your graveyard");

    public GolgariFindbroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Golgari Findbroker enters the battlefield, return target permanent card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public GolgariFindbroker(final GolgariFindbroker card) {
        super(card);
    }

    @Override
    public GolgariFindbroker copy() {
        return new GolgariFindbroker(this);
    }
}
