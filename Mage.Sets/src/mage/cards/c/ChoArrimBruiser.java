
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class ChoArrimBruiser extends CardImpl {

    public ChoArrimBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

		// Whenever Cho-Arrim Bruiser attacks, you may tap up to two target creatures.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private ChoArrimBruiser(final ChoArrimBruiser card) {
        super(card);
    }

    @Override
    public ChoArrimBruiser copy() {
        return new ChoArrimBruiser(this);
    }
}
