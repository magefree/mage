
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
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_ANOTHER_TARGET_CREATURE;

/**
 *
 * @author LevelX2
 */
public final class IcefeatherAven extends CardImpl {

    public IcefeatherAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {1}{G}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{1}{G}{U}")));
        // When Icefeather Aven is turned face up, you may return another target creature to its owner's hand.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ReturnToHandTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(FILTER_ANOTHER_TARGET_CREATURE));
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
