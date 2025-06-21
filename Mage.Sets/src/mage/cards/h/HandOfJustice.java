package mage.cards.h;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HandOfJustice extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("untapped white creatures you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(TappedPredicate.UNTAPPED);
    }

    public HandOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // {tap}, Tap three untapped white creatures you control: Destroy target creature.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(3, filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HandOfJustice(final HandOfJustice card) {
        super(card);
    }

    @Override
    public HandOfJustice copy() {
        return new HandOfJustice(this);
    }
}
