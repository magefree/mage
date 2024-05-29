
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MarionetteApprentice extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another creature or artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ARTIFACT.getPredicate()));
    }

    public MarionetteApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Fabricate 1
        this.addAbility(new FabricateAbility(1));

        // Whenever another creature or artifact you control is put into a graveyard from the battlefield, each opponent loses 1 life.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false,
                filter, false
        ));
    }

    private MarionetteApprentice(final MarionetteApprentice card) {
        super(card);
    }

    @Override
    public MarionetteApprentice copy() {
        return new MarionetteApprentice(this);
    }
}
