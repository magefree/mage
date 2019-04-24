package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofFromMonocoloredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxOfTheGuildpact extends CardImpl {

    public SphinxOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sphinx of the Guildpact is all colors.
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this} is all colors")));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hexproof from mono colored
        this.addAbility(HexproofFromMonocoloredAbility.getInstance());
    }

    private SphinxOfTheGuildpact(final SphinxOfTheGuildpact card) {
        super(card);
    }

    @Override
    public SphinxOfTheGuildpact copy() {
        return new SphinxOfTheGuildpact(this);
    }
}
