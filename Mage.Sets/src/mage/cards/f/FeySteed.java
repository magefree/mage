package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author zeffirojoe
 */
public final class FeySteed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target attacking creature you control");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("a creature or planeswalker you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);

        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public FeySteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{W}{W}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Fey Steed attacks, another target attacking creature you control gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Whenever a creature or planeswalker you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new DrawCardSourceControllerEffect(1),
                filter2, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.NONE, true));
    }

    private FeySteed(final FeySteed card) {
        super(card);
    }

    @Override
    public FeySteed copy() {
        return new FeySteed(this);
    }
}
