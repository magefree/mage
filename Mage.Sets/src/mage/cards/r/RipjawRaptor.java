
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class RipjawRaptor extends CardImpl {

    public RipjawRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Enrage</i> &mdash; Whenever Ripjaw Raptor is dealt damage, draw a card.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false, true);
        this.addAbility(ability);
    }

    public RipjawRaptor(final RipjawRaptor card) {
        super(card);
    }

    @Override
    public RipjawRaptor copy() {
        return new RipjawRaptor(this);
    }
}
