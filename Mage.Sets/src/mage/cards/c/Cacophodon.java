
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class Cacophodon extends CardImpl {

    public Cacophodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // <i>Enrage</i> — Whenever Cacophodon is dealt damage, untap target permanent.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new UntapTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    public Cacophodon(final Cacophodon card) {
        super(card);
    }

    @Override
    public Cacophodon copy() {
        return new Cacophodon(this);
    }
}
