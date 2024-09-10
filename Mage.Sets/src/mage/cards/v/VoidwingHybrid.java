package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.ProliferatedControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoidwingHybrid extends CardImpl {

    public VoidwingHybrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When you proliferate, return Voidwing Hybrid from your graveyard to your hand.
        this.addAbility(new ProliferatedControllerTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), false
        ).setTriggerPhrase("When you proliferate, "));
    }

    private VoidwingHybrid(final VoidwingHybrid card) {
        super(card);
    }

    @Override
    public VoidwingHybrid copy() {
        return new VoidwingHybrid(this);
    }
}
