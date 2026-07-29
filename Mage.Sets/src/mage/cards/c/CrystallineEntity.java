package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CrystallineEntity extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public CrystallineEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, if you cast it, destroy all nonartifact creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter))
            .withInterveningIf(CastFromEverywhereSourceCondition.instance));
    }

    private CrystallineEntity(final CrystallineEntity card) {
        super(card);
    }

    @Override
    public CrystallineEntity copy() {
        return new CrystallineEntity(this);
    }
}
