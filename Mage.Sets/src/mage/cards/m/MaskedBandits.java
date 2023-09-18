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
public final class MaskedBandits extends CardImpl {

    public MaskedBandits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // {2}, Exile Masked Bandits from your hand: Target land gains "{T}: Add {B}, {R}, or {G}" until Masked Bandits is cast from exile. You may cast Masked Bandits for as long as it remains exiled.
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("BRG"));
    }

    private MaskedBandits(final MaskedBandits card) {
        super(card);
    }

    @Override
    public MaskedBandits copy() {
        return new MaskedBandits(this);
    }
}
