package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CantCastDuringYourTurnEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DragonlordDromoka extends CardImpl {

    public DragonlordDromoka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Dragonlord Dromoka can't be countered
        this.addAbility(new CantBeCounteredSourceAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(new CantCastDuringYourTurnEffect()));
        
    }

    private DragonlordDromoka(final DragonlordDromoka card) {
        super(card);
    }

    @Override
    public DragonlordDromoka copy() {
        return new DragonlordDromoka(this);
    }
}
