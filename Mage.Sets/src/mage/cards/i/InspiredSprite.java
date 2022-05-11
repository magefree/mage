
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;

/**
 *
 * @author Loki
 */
public final class InspiredSprite extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Wizard spell");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public InspiredSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, true));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new TapSourceCost()));
    }

    private InspiredSprite(final InspiredSprite card) {
        super(card);
    }

    @Override
    public InspiredSprite copy() {
        return new InspiredSprite(this);
    }
}
