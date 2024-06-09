package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33, North (Merfolk Looter)
 *     
 */
public final class CephalidBroker extends CardImpl {

    public CephalidBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.CEPHALID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Target player draws two cards, then discards two cards.        
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(2), new TapSourceCost());
        ability.addEffect(new DiscardTargetEffect(2).setText(", then discards two cards"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CephalidBroker(final CephalidBroker card) {
        super(card);
    }

    @Override
    public CephalidBroker copy() {
        return new CephalidBroker(this);
    }
}
