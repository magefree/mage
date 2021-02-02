
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CastOnlyIfYouHaveCastAnotherSpellEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
*
* @author LevelX2
*/
public final class IllusoryAngel extends CardImpl {

    public IllusoryAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cast Illusory Angel only if you've cast another spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CastOnlyIfYouHaveCastAnotherSpellEffect()));
    }

    private IllusoryAngel(final IllusoryAngel card) {
        super(card);
    }

    @Override
    public IllusoryAngel copy() {
        return new IllusoryAngel(this);
    }
}
