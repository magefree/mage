package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DutifulReplicator extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("token you control not named Dutiful Replicator");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(Predicates.not(new NamePredicate("Dutiful Replicator")));
    }

    public DutifulReplicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Dutiful Replicator enters the battlefield, you may pay {1}. When you do, create a token that's a copy of target token you control not named Dutiful Replicator.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new CreateTokenCopyTargetEffect(), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(ability, new GenericManaCost(1), "Pay {1}?")
        ));
    }

    private DutifulReplicator(final DutifulReplicator card) {
        super(card);
    }

    @Override
    public DutifulReplicator copy() {
        return new DutifulReplicator(this);
    }
}
