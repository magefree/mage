package mage.cards.v;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VampireOfTheDireMoon extends CardImpl {

    public VampireOfTheDireMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private VampireOfTheDireMoon(final VampireOfTheDireMoon card) {
        super(card);
    }

    @Override
    public VampireOfTheDireMoon copy() {
        return new VampireOfTheDireMoon(this);
    }
}
