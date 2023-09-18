package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.GainProtectionFromColorSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantSerraArchangel extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another untapped creature you control with flying");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public RadiantSerraArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tap another untapped creature you control with flying: Radiant, Serra Archangel gains protection from the color of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainProtectionFromColorSourceEffect(Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(filter))
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private RadiantSerraArchangel(final RadiantSerraArchangel card) {
        super(card);
    }

    @Override
    public RadiantSerraArchangel copy() {
        return new RadiantSerraArchangel(this);
    }
}
