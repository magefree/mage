package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrookclawElder extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.BIRD, "untapped Birds you control");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.WIZARD, "untapped Wizards you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public CrookclawElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tap two untapped Birds you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(new TargetControlledPermanent(
                        2, 2, filter, true
                ))
        );
        this.addAbility(ability);

        // Tap two untapped Wizards you control: Target creature gains flying until end of turn.
        ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(
                        2, 2, filter2, true
                ))
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CrookclawElder(final CrookclawElder card) {
        super(card);
    }

    @Override
    public CrookclawElder copy() {
        return new CrookclawElder(this);
    }
}
