
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class EruptingDreadwolf extends CardImpl {

    public EruptingDreadwolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Whenever Erupting Dreadwolf attacks, it deals 2 damage to any target.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(2, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public EruptingDreadwolf(final EruptingDreadwolf card) {
        super(card);
    }

    @Override
    public EruptingDreadwolf copy() {
        return new EruptingDreadwolf(this);
    }
}
