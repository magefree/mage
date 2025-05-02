package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WoodlandLiege extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BEAST);

    public WoodlandLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Beast you control enters, draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private WoodlandLiege(final WoodlandLiege card) {
        super(card);
    }

    @Override
    public WoodlandLiege copy() {
        return new WoodlandLiege(this);
    }
}
