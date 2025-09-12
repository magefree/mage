package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaryJaneWatson extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIDER);

    public MaryJaneWatson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Spider you control enters, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ).setTriggersLimitEachTurn(1));
    }

    private MaryJaneWatson(final MaryJaneWatson card) {
        super(card);
    }

    @Override
    public MaryJaneWatson copy() {
        return new MaryJaneWatson(this);
    }
}
