package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToThisTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianZombieToken;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class NestedGhoul extends CardImpl {

    public NestedGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever a source deals damage to Nested Ghoul, create a 2/2 black Phyrexian Zombie creature token.
        this.addAbility(new SourceDealsDamageToThisTriggeredAbility(new CreateTokenEffect(new PhyrexianZombieToken())));
    }

    private NestedGhoul(final NestedGhoul card) {
        super(card);
    }

    @Override
    public NestedGhoul copy() {
        return new NestedGhoul(this);
    }

}
