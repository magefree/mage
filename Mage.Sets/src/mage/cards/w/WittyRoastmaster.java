package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WittyRoastmaster extends CardImpl {

    public WittyRoastmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Alliance — Whenever another creature enters the battlefield under your control, Witty Roastmaster deals 1 damage to each opponent.
        this.addAbility(new AllianceAbility(new DamagePlayersEffect(1, TargetController.OPPONENT)));
    }

    private WittyRoastmaster(final WittyRoastmaster card) {
        super(card);
    }

    @Override
    public WittyRoastmaster copy() {
        return new WittyRoastmaster(this);
    }
}
