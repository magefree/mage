package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YathanTombguard extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public YathanTombguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a creature you control with a counter on it deals combat damage to a player, you draw a card and you lose 1 life.
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true),
                filter, false, SetTargetPointer.NONE, true
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private YathanTombguard(final YathanTombguard card) {
        super(card);
    }

    @Override
    public YathanTombguard copy() {
        return new YathanTombguard(this);
    }
}
