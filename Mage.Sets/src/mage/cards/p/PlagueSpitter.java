
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LoneFox

 */
public final class PlagueSpitter extends CardImpl {

    public PlagueSpitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, Plague Spitter deals 1 damage to each creature and each player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageEverythingEffect(1), TargetController.YOU, false));
        // When Plague Spitter dies, Plague Spitter deals 1 damage to each creature and each player.
        this.addAbility(new DiesSourceTriggeredAbility(new DamageEverythingEffect(1), false));
    }

    private PlagueSpitter(final PlagueSpitter card) {
        super(card);
    }

    @Override
    public PlagueSpitter copy() {
        return new PlagueSpitter(this);
    }
}
