package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GatewaySneak extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GATE, "a Gate");

    public GatewaySneak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a Gate enters the battlefield under your control, Gateway Sneak can't be blocked this turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), filter
        ));

        // Whenever Gateway Sneak deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private GatewaySneak(final GatewaySneak card) {
        super(card);
    }

    @Override
    public GatewaySneak copy() {
        return new GatewaySneak(this);
    }
}
