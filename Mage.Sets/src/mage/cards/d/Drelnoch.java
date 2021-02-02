
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class Drelnoch extends CardImpl {

    public Drelnoch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.YETI);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Drelnoch becomes blocked, you may draw two cards.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new DrawCardSourceControllerEffect(2), true));
    }

    private Drelnoch(final Drelnoch card) {
        super(card);
    }

    @Override
    public Drelnoch copy() {
        return new Drelnoch(this);
    }
}
