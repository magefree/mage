
package mage.cards.g;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
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
public final class GlitteringLynx extends CardImpl {

    public GlitteringLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Prevent all damage that would be dealt to Glittering Lynx.
        this.addAbility(GlitteringLynxAbility.getInstance());
        // {2}: Until end of turn, Glittering Lynx loses "Prevent all damage that would be dealt to Glittering Lynx." Any player may activate this ability.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAbilitySourceEffect(GlitteringLynxAbility.getInstance(), Duration.EndOfTurn).setText("Until end of turn, {this} loses \"Prevent all damage that would be dealt to {this}.\""), new ManaCostsImpl<>("{2}"));
        ability2.setMayActivate(TargetController.ANY);
        ability2.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability2);
    }

    private GlitteringLynx(final GlitteringLynx card) {
        super(card);
    }

    @Override
    public GlitteringLynx copy() {
        return new GlitteringLynx(this);
    }

}

class GlitteringLynxAbility extends StaticAbility {

    private static final GlitteringLynxAbility instance =  new GlitteringLynxAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static GlitteringLynxAbility getInstance() {
        return instance;
    }

    public GlitteringLynxAbility() {
        super(Zone.BATTLEFIELD, new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield));
    }

    @Override
    public GlitteringLynxAbility copy() {
        return instance;
    }

}
