package mage.cards.c;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.common.UntapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Crackleburr extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("untapped red creatures you control");
    private static final FilterControlledPermanent filter2 = new FilterControlledCreaturePermanent("tapped blue creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(TappedPredicate.UNTAPPED);

        filter2.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(TappedPredicate.TAPPED);
    }

    public Crackleburr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {UR}{UR}, {tap}, Tap two untapped red creatures you control: Crackleburr deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new ManaCostsImpl<>("{U/R}{U/R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(2, filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {UR}{UR}, {untap}, Untap two tapped blue creatures you control: Return target creature to its owner's hand.
        Ability ability2 = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{U/R}{U/R}"));
        ability2.addCost(new UntapSourceCost());
        ability2.addCost(new UntapTargetCost(new TargetControlledPermanent(2, filter2)));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private Crackleburr(final Crackleburr card) {
        super(card);
    }

    @Override
    public Crackleburr copy() {
        return new Crackleburr(this);
    }
}
