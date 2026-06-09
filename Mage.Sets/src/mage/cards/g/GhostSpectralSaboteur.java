package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GhostSpectralSaboteur extends CardImpl {

    public GhostSpectralSaboteur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Intangibility -- Ghost can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility().withFlavorWord("Intangibility"));
    }

    private GhostSpectralSaboteur(final GhostSpectralSaboteur card) {
        super(card);
    }

    @Override
    public GhostSpectralSaboteur copy() {
        return new GhostSpectralSaboteur(this);
    }
}
