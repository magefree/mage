
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IcefeatherAven extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public IcefeatherAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {1}{G}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{G}{U}")));
        // When Icefeather Aven is turned face up, you may return another target creature to its owner's hand.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ReturnToHandTargetEffect(), false, true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    private IcefeatherAven(final IcefeatherAven card) {
        super(card);
    }

    @Override
    public IcefeatherAven copy() {
        return new IcefeatherAven(this);
    }
}
