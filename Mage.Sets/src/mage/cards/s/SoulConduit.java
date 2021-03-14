package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExchangeLifeTwoTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class SoulConduit extends CardImpl {

    public SoulConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {6}, {tap}: Two target players exchange life totals.
        Ability ability = new SimpleActivatedAbility(new ExchangeLifeTwoTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer(2));
        this.addAbility(ability);
    }

    private SoulConduit(final SoulConduit card) {
        super(card);
    }

    @Override
    public SoulConduit copy() {
        return new SoulConduit(this);
    }
}
