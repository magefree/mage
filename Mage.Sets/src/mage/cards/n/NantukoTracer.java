
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class NantukoTracer extends CardImpl {

    public NantukoTracer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Nantuko Tracer enters the battlefield, you may put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(false), true);
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private NantukoTracer(final NantukoTracer card) {
        super(card);
    }

    @Override
    public NantukoTracer copy() {
        return new NantukoTracer(this);
    }
}
