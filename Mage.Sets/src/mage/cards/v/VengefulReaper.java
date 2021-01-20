package mage.cards.v;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulReaper extends CardImpl {

    public VengefulReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Foretell {1}{B}
        this.addAbility(new ForetellAbility(this, "{1}{B}"));
    }

    private VengefulReaper(final VengefulReaper card) {
        super(card);
    }

    @Override
    public VengefulReaper copy() {
        return new VengefulReaper(this);
    }
}
