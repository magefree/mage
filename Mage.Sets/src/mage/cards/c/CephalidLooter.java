package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author cbt33, Loki (Merfolk Looter)
 */
public final class CephalidLooter extends CardImpl {

    public CephalidLooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {tap}: Target player draws a card, then discards a card.
        Ability ability = new SimpleActivatedAbility(new DrawDiscardTargetEffect(1, 1), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CephalidLooter(final CephalidLooter card) {
        super(card);
    }

    @Override
    public CephalidLooter copy() {
        return new CephalidLooter(this);
    }
}
