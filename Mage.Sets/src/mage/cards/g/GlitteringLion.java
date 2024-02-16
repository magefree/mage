
package mage.cards.g;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author L_J
 */
public final class GlitteringLion extends CardImpl {

    public GlitteringLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all damage that would be dealt to Glittering Lion.
        this.addAbility(GlitteringLionAbility.getInstance());
        // {3}: Until end of turn, Glittering Lion loses "Prevent all damage that would be dealt to Glittering Lion." Any player may activate this ability.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAbilitySourceEffect(GlitteringLionAbility.getInstance(), Duration.EndOfTurn).setText("Until end of turn, {this} loses \"Prevent all damage that would be dealt to {this}.\" Any player may activate this ability"), new ManaCostsImpl<>("{3}"));
        ability2.setMayActivate(TargetController.ANY);
        this.addAbility(ability2);
    }

    private GlitteringLion(final GlitteringLion card) {
        super(card);
    }

    @Override
    public GlitteringLion copy() {
        return new GlitteringLion(this);
    }

}

class GlitteringLionAbility extends StaticAbility {

    private static final GlitteringLionAbility instance =  new GlitteringLionAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static GlitteringLionAbility getInstance() {
        return instance;
    }

    public GlitteringLionAbility() {
        super(Zone.BATTLEFIELD, new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield));
    }

    @Override
    public GlitteringLionAbility copy() {
        return instance;
    }

}
