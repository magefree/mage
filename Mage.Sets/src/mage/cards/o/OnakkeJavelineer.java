package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnakkeJavelineer extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer("player or battle");

    static {
        filter.getPermanentFilter().add(CardType.BATTLE.getPredicate());
    }

    public OnakkeJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Onakke Javelineer deals 2 damage to target player or battle.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new TapSourceCost());
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);
    }

    private OnakkeJavelineer(final OnakkeJavelineer card) {
        super(card);
    }

    @Override
    public OnakkeJavelineer copy() {
        return new OnakkeJavelineer(this);
    }
}
