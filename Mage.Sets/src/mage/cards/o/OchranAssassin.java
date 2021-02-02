package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class OchranAssassin extends CardImpl {

    public OchranAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // All creatures able to block Ochran Assassin do so.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new MustBeBlockedByAllSourceEffect()
        ));
    }

    private OchranAssassin(final OchranAssassin card) {
        super(card);
    }

    @Override
    public OchranAssassin copy() {
        return new OchranAssassin(this);
    }
}
