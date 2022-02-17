package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangOfShigeki extends CardImpl {

    public FangOfShigeki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private FangOfShigeki(final FangOfShigeki card) {
        super(card);
    }

    @Override
    public FangOfShigeki copy() {
        return new FangOfShigeki(this);
    }
}
