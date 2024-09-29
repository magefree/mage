package mage.cards.s;

import mage.abilities.keyword.CraftAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunbirdStandard extends CardImpl {

    public SunbirdStandard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.secondSideCardClazz = mage.cards.s.SunbirdEffigy.class;

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Craft with one or more {5}
        this.addAbility(new CraftAbility(
                "{5}", "one or more", "other permanents " +
                "you control and/or cards in your graveyard", 1, Integer.MAX_VALUE
        ));
    }

    private SunbirdStandard(final SunbirdStandard card) {
        super(card);
    }

    @Override
    public SunbirdStandard copy() {
        return new SunbirdStandard(this);
    }
}
