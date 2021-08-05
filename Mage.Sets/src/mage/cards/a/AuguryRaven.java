package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class AuguryRaven extends CardImpl {

    public AuguryRaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Foretell {1}{U}
        this.addAbility(new ForetellAbility(this, "{1}{U}"));
    }

    private AuguryRaven(final AuguryRaven card) {
        super(card);
    }

    @Override
    public AuguryRaven copy() {
        return new AuguryRaven(this);
    }
}
