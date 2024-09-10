
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class MatcaRioters extends CardImpl {

    public MatcaRioters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Domain - Matca Rioters's power and toughness are each equal to the number of basic land types among lands you control.
        Effect effect = new SetBasePowerToughnessSourceEffect(DomainValue.REGULAR);
        effect.setText("<i>Domain</i> &mdash; {this}'s power and toughness are each equal to the number of basic land types among lands you control.");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect).addHint(DomainHint.instance));
    }

    private MatcaRioters(final MatcaRioters card) {
        super(card);
    }

    @Override
    public MatcaRioters copy() {
        return new MatcaRioters(this);
    }
}
