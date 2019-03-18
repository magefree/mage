
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class RuinGhost extends CardImpl {

    public RuinGhost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, {tap}: Exile target land you control, then return it to the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new ManaCostsImpl("{W"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ReturnToBattlefieldUnderYourControlTargetEffect(true));
        ability.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    public RuinGhost(final RuinGhost card) {
        super(card);
    }

    @Override
    public RuinGhost copy() {
        return new RuinGhost(this);
    }
}
