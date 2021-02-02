
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class EbonDrake extends CardImpl {

    public EbonDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a player casts a spell, you lose 1 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new LoseLifeSourceControllerEffect(1), false));
    }

    private EbonDrake(final EbonDrake card) {
        super(card);
    }

    @Override
    public EbonDrake copy() {
        return new EbonDrake(this);
    }
}
