
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class FiremaneAvenger extends CardImpl {

    public FiremaneAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        this.subtype.add(SubType.ANGEL);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Battalion - Whenever Firemane Avenger and at least two other creatures attack, Firemane Avenger deals 3 damage to any target and you gain 3 life.
        Ability ability = new BattalionAbility(new DamageTargetEffect(3));
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new GainLifeEffect(3));
        this.addAbility(ability);
    }

    private FiremaneAvenger(final FiremaneAvenger card) {
        super(card);
    }

    @Override
    public FiremaneAvenger copy() {
        return new FiremaneAvenger(this);
    }
}
