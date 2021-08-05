package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CycloneSummoner extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(Predicates.not(SubType.GIANT.getPredicate()));
        filter.add(Predicates.not(SubType.WIZARD.getPredicate()));
    }

    public CycloneSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Cyclone Summoner enters the battlefield, if you cast it from your hand, return all permanents to their owners' hands except for Giants, Wizards, and lands.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new ReturnToHandFromBattlefieldAllEffect(filter), false
                ), CastFromHandSourcePermanentCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it from your hand, return all permanents to their owners' hands " +
                "except for Giants, Wizards, and lands."
        ), new CastFromHandWatcher());
    }

    private CycloneSummoner(final CycloneSummoner card) {
        super(card);
    }

    @Override
    public CycloneSummoner copy() {
        return new CycloneSummoner(this);
    }
}
