
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;


/**
 *
 * @author MarcoMarin
 */
public final class GaeasAvenger extends CardImpl {
    
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts opponent control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GaeasAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Gaea's Avenger's power and toughness are each equal to 1 plus the number of artifacts your opponents control.
        
        SetBasePowerToughnessSourceEffect effect = new SetBasePowerToughnessSourceEffect(new IntPlusDynamicValue(1, new PermanentsOnBattlefieldCount(filter)), Duration.EndOfGame);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    private GaeasAvenger(final GaeasAvenger card) {
        super(card);
    }

    @Override
    public GaeasAvenger copy() {
        return new GaeasAvenger(this);
    }
}

