package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurgeConductor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("another nontoken artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SurgeConductor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever another nontoken artifact you control enters, proliferate.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new ProliferateEffect(), filter));
    }

    private SurgeConductor(final SurgeConductor card) {
        super(card);
    }

    @Override
    public SurgeConductor copy() {
        return new SurgeConductor(this);
    }
}
