package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author BursegSardaukar
 */
public final class GoblinSnowman extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature it's blocking");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKED_BY);
    }

    public GoblinSnowman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Whenever Goblin Snowman blocks, prevent all combat damage that would be dealt to and dealt by it this turn.
        Ability ability = new BlocksSourceTriggeredAbility(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn)
                .setText("prevent all combat damage that would be dealt to"), false);
        ability.addEffect(new PreventCombatDamageToSourceEffect(Duration.EndOfTurn)
                .setText("and dealt by it this turn"));
        this.addAbility(ability);

        //{T}: Goblin Snowman deals 1 damage to target creature it's blocking.
        ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GoblinSnowman(final GoblinSnowman card) {
        super(card);
    }

    @Override
    public GoblinSnowman copy() {
        return new GoblinSnowman(this);
    }
}
