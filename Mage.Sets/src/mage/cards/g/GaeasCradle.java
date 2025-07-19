package mage.cards.g;

import mage.Mana;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class GaeasCradle extends CardImpl {

    public GaeasCradle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {G} for each creature you control.
        this.addAbility(new DynamicManaAbility(
                Mana.GreenMana(1), CreaturesYouControlCount.SINGULAR
        ).addHint(CreaturesYouControlHint.instance));
    }

    private GaeasCradle(final GaeasCradle card) {
        super(card);
    }

    @Override
    public GaeasCradle copy() {
        return new GaeasCradle(this);
    }
}
