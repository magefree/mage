
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class NiblisOfTheUrn extends CardImpl {

    public NiblisOfTheUrn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Niblis of the Urn attacks, you may tap target creature.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NiblisOfTheUrn(final NiblisOfTheUrn card) {
        super(card);
    }

    @Override
    public NiblisOfTheUrn copy() {
        return new NiblisOfTheUrn(this);
    }
}
