
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class BallynockTrapper extends CardImpl {

    private static final FilterSpell filterWhiteSpell = new FilterSpell("a white spell");

    static {
        filterWhiteSpell.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BallynockTrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.KITHKIN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filterWhiteSpell, true));
    }

    private BallynockTrapper(final BallynockTrapper card) {
        super(card);
    }

    @Override
    public BallynockTrapper copy() {
        return new BallynockTrapper(this);
    }
}
