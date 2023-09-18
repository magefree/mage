package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeshirosLivingLegacy extends CardImpl {

    public SeshirosLivingLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);
        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private SeshirosLivingLegacy(final SeshirosLivingLegacy card) {
        super(card);
    }

    @Override
    public SeshirosLivingLegacy copy() {
        return new SeshirosLivingLegacy(this);
    }
}
