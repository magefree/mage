package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AwakenedSkyclave extends CardImpl {

    public AwakenedSkyclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);
        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as Awakened Skyclave is on the battlefield, it's a land in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new AddCardTypeSourceEffect(Duration.WhileOnBattlefield)
                .setText("as long as {this} is on the battlefield, it's a land in addition to its other types")));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private AwakenedSkyclave(final AwakenedSkyclave card) {
        super(card);
    }

    @Override
    public AwakenedSkyclave copy() {
        return new AwakenedSkyclave(this);
    }
}
