package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HeroInTraining extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HERO, "you control another Hero");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HeroInTraining(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, draw a card. If you control another Hero, you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(
            new GainLifeEffect(2),
            new PermanentsOnTheBattlefieldCondition(filter),
            "If you control another Hero, you gain 2 life"
        ));
        this.addAbility(ability);
    }

    private HeroInTraining(final HeroInTraining card) {
        super(card);
    }

    @Override
    public HeroInTraining copy() {
        return new HeroInTraining(this);
    }
}
