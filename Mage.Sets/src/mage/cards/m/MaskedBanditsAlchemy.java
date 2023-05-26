package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskedBanditsAlchemy extends CardImpl {

    public MaskedBanditsAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // {2}, Exile Masked Bandits from your hand: Target land gains "{T}: Add {B}, {R}, or {G}" until Masked Bandits is cast from exile. You may cast Masked Bandits for as long as it remains exiled.
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("BRG",1));
    }

    private MaskedBanditsAlchemy(final MaskedBanditsAlchemy card) {
        super(card);
    }

    @Override
    public MaskedBanditsAlchemy copy() {
        return new MaskedBanditsAlchemy(this);
    }
}
