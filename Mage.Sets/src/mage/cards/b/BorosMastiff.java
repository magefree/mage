

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */


public final class BorosMastiff extends CardImpl {

    public BorosMastiff (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Battalion — Whenever Boros Mastiff and at least two other cretaures attack, Boros Mastiff gets lifelink until end of turn.
        this.addAbility(new BattalionAbility(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn)));

    }

    private BorosMastiff(final BorosMastiff card) {
        super(card);
    }

    @Override
    public BorosMastiff copy() {
        return new BorosMastiff(this);
    }

}
