package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class FilthyCur extends CardImpl {

    public FilthyCur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Filthy Cur is dealt damage, you lose that much life.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new LoseLifeSourceControllerEffect(SavedDamageValue.MUCH), false
        ));

    }

    private FilthyCur(final FilthyCur card) {
        super(card);
    }

    @Override
    public FilthyCur copy() {
        return new FilthyCur(this);
    }
}
