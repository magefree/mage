package mage.cards.t;

import mage.Mana;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Backfir3
 */

public final class TolarianAcademy extends CardImpl {

    public TolarianAcademy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        this.addAbility(new DynamicManaAbility(Mana.BlueMana(1), ArtifactYouControlCount.instance).addHint(ArtifactYouControlHint.instance));
    }

    private TolarianAcademy(final TolarianAcademy card) {
        super(card);
    }

    @Override
    public TolarianAcademy copy() {
        return new TolarianAcademy(this);
    }
}
