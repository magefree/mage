
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DemonPossessedWitch extends CardImpl {

    public DemonPossessedWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // When this creature transforms into Demon-Possessed Witch, you may destroy target creature.
        Ability ability = new TransformIntoSourceTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DemonPossessedWitch(final DemonPossessedWitch card) {
        super(card);
    }

    @Override
    public DemonPossessedWitch copy() {
        return new DemonPossessedWitch(this);
    }
}
