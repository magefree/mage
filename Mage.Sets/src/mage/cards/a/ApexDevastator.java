package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ApexDevastator extends CardImpl {

    public ApexDevastator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{G}{G}");

        this.subtype.add(SubType.CHIMERA);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Cascade
        this.addAbility(new CascadeAbility(false));

        // Cascade
        this.addAbility(new CascadeAbility(false));

        // Cascade
        this.addAbility(new CascadeAbility(false));

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private ApexDevastator(final ApexDevastator card) {
        super(card);
    }

    @Override
    public ApexDevastator copy() {
        return new ApexDevastator(this);
    }
}
