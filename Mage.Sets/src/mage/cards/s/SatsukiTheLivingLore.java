package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SatsukiTheLivingLore extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.SAGA);
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("Saga or enchantment creature you control");
    private static final FilterCard filter3
            = new FilterCard("Saga card from your graveyard");

    static {
        filter.add(Predicates.or(
                SubType.SAGA.getPredicate(),
                Predicates.and(
                        CardType.ENCHANTMENT.getPredicate(),
                        CardType.CREATURE.getPredicate()
                )
        ));
        filter.add(SubType.SAGA.getPredicate());
    }

    public SatsukiTheLivingLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Put a lore counter on each Saga you control. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(new AddCountersAllEffect(
                CounterType.LORE.createInstance(), filter
        ), new TapSourceCost()));

        // When Satsuki, the Living Lore dies, choose up to one —
        // • Return target Saga or enchantment creature you control to its owner's hand.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnToHandTargetEffect());
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);
        ability.addTarget(new TargetPermanent(filter2));

        // • Return target Saga card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter3));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private SatsukiTheLivingLore(final SatsukiTheLivingLore card) {
        super(card);
    }

    @Override
    public SatsukiTheLivingLore copy() {
        return new SatsukiTheLivingLore(this);
    }
}
