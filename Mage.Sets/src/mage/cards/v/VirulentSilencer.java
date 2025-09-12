package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VirulentSilencer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a nontoken artifact creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public VirulentSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a nontoken artifact creature you control deals combat damage to a player, that player gets two poison counters.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddPoisonCounterTargetEffect(2), filter,
                false, SetTargetPointer.PLAYER, true, true
        ));
    }

    private VirulentSilencer(final VirulentSilencer card) {
        super(card);
    }

    @Override
    public VirulentSilencer copy() {
        return new VirulentSilencer(this);
    }
}
