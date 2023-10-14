package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class LightningShrieker extends CardImpl {

    public LightningShrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of the end step, Lightning Shrieker's owner shuffles it into their library.
        Effect effect = new ShuffleIntoLibrarySourceEffect();
        effect.setText("{this}'s owner shuffles it into their library.");
        this.addAbility(new BeginningOfEndStepTriggeredAbility(effect, TargetController.NEXT, false));
    }

    private LightningShrieker(final LightningShrieker card) {
        super(card);
    }

    @Override
    public LightningShrieker copy() {
        return new LightningShrieker(this);
    }
}
