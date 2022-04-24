package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiveteersDecoy extends CardImpl {

    public RiveteersDecoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Riveteers Decoy must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Blitz {3}{G}
        this.addAbility(new BlitzAbility(this, "{3}{G}"));
    }

    private RiveteersDecoy(final RiveteersDecoy card) {
        super(card);
    }

    @Override
    public RiveteersDecoy copy() {
        return new RiveteersDecoy(this);
    }
}
