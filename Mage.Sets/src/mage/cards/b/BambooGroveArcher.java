package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BambooGroveArcher extends CardImpl {

    public BambooGroveArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Channel — {4}{G}, Discard Bamboo Grove Archer: Destroy target creature with flying.
        Ability ability = new ChannelAbility("{4}{G}", new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private BambooGroveArcher(final BambooGroveArcher card) {
        super(card);
    }

    @Override
    public BambooGroveArcher copy() {
        return new BambooGroveArcher(this);
    }
}
