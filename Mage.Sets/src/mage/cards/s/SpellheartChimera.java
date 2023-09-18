
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;

/**
 *
 * @author LevelX2
 */
public final class SpellheartChimera extends CardImpl {

    public SpellheartChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.CHIMERA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Spellheart Chimera's power is equal to the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(
                new CardsInControllerGraveyardCount(new FilterInstantOrSorceryCard("instant and sorcery cards in your graveyard")))));
    }

    private SpellheartChimera(final SpellheartChimera card) {
        super(card);
    }

    @Override
    public SpellheartChimera copy() {
        return new SpellheartChimera(this);
    }
}
