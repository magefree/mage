package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class DeadeyeHarpooner extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public DeadeyeHarpooner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Revolt</i> &mdash; When Deadeye Harpooner enters the battlefield, if a permanent you controlled left the battlefield this turn, destroy target tapped creature an opponent controls.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false),
                RevoltCondition.instance, "When {this} enters the battlefield, if a permanent you controlled " +
                "left the battlefield this turn, destroy target tapped creature an opponent controls."
        );
        ability.setAbilityWord(AbilityWord.REVOLT);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability, new RevoltWatcher());
    }

    private DeadeyeHarpooner(final DeadeyeHarpooner card) {
        super(card);
    }

    @Override
    public DeadeyeHarpooner copy() {
        return new DeadeyeHarpooner(this);
    }
}
