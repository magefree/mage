package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class CollationTrimatrix extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
        new FilterControlledCreaturePermanent("untapped artifact creatures you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CollationTrimatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tap three untapped artifact creatures you control: You draw a card and lose 1 life.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(1, true), new TapTargetCost(3, filter)
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));
        this.addAbility(ability);
    }

    private CollationTrimatrix(final CollationTrimatrix card) {
        super(card);
    }

    @Override
    public CollationTrimatrix copy() {
        return new CollationTrimatrix(this);
    }
}
