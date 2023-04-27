
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class InsidiousMist extends CardImpl {

    public InsidiousMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.color.setBlue(true);

        this.nightCard = true;

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Insideous Mist can't block and can't be blocked.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockSourceEffect(Duration.WhileOnBattlefield));
        Effect effect = new CantBeBlockedSourceEffect();
        effect.setText("and can't be blocked");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever Insideous Mist attacks and isn't blocked, you may pay {2}{B}. If you do, transform it.
        this.addAbility(new TransformAbility());
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{2}{B}"), "Pay {2}{B} to transform?")));
    }

    private InsidiousMist(final InsidiousMist card) {
        super(card);
    }

    @Override
    public InsidiousMist copy() {
        return new InsidiousMist(this);
    }
}
