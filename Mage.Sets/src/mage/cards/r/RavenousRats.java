

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class RavenousRats extends CardImpl {

    public RavenousRats (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RavenousRats(final RavenousRats card) {
        super(card);
    }

    @Override
    public RavenousRats copy() {
        return new RavenousRats(this);
    }

}
