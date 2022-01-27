package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManaGeode extends CardImpl {

    public ManaGeode(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When Mana Geode enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ManaGeode(final ManaGeode card) {
        super(card);
    }

    @Override
    public ManaGeode copy() {
        return new ManaGeode(this);
    }
}
