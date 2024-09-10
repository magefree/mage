
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author Styxo
 */
public final class C3POAndR2D2 extends CardImpl {

    public C3POAndR2D2(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When C-3PO and R2D2 leaves the battlefield, you gain 4 life and draw a card.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new GainLifeEffect(4), false);
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("and draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Repair 2
        this.addAbility(new RepairAbility(2));
    }

    private C3POAndR2D2(final C3POAndR2D2 card) {
        super(card);
    }

    @Override
    public C3POAndR2D2 copy() {
        return new C3POAndR2D2(this);
    }
}
