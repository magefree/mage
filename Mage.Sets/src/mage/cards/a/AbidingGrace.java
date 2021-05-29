package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class AbidingGrace extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card with mana value 1 from your graveyard");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 1));
    }

    public AbidingGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // At the beginning of your end step, choose one —
        // • You gain 1 life.
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new GainLifeEffect(1), false);

        // • Return target creature card with mana value 1 from your graveyard to the battlefield.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private AbidingGrace(final AbidingGrace card) {
        super(card);
    }

    @Override
    public AbidingGrace copy() {
        return new AbidingGrace(this);
    }
}
