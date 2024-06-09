package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NezumiLinkbreaker extends CardImpl {

    public NezumiLinkbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Nezumi Linkbreaker dies, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new MercenaryToken())));
    }

    private NezumiLinkbreaker(final NezumiLinkbreaker card) {
        super(card);
    }

    @Override
    public NezumiLinkbreaker copy() {
        return new NezumiLinkbreaker(this);
    }
}
