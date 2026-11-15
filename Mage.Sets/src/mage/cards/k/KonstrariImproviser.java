package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.game.permanent.token.HeartwoodToken;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KonstrariImproviser extends PrepareCard {

    public KonstrariImproviser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/G}", "Soul Tether", new CardType[]{CardType.SORCERY}, "{2}{R/G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Soul Tether
        // Sorcery {2}{R/G}
        // Create a Heartwood token
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new HeartwoodToken()));
    }

    private KonstrariImproviser(final KonstrariImproviser card) {
        super(card);
    }

    @Override
    public KonstrariImproviser copy() {
        return new KonstrariImproviser(this);
    }
}
