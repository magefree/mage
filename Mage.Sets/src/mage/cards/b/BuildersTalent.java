package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.permanent.token.WallWhiteToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BuildersTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("noncreature, nonland permanents");
    private static final FilterCard filter2 = new FilterNonlandCard("noncreature, nonland permanent card from your graveyard");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter2.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter2.add(PermanentPredicate.instance);
    }

    public BuildersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Builder's Talent enters, create a 0/4 white Wall creature token with defender.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WallWhiteToken())));

        // {W}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{W}"));

        // Whenever one or more noncreature, nonland permanents you control enter, put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldOneOrMoreTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filter, TargetController.YOU
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {4}{W}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{4}{W}"));

        // When this Class becomes level 3, return target noncreature, nonland permanent card from your graveyard to the battlefield.
        ability = new BecomesClassLevelTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), 3);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private BuildersTalent(final BuildersTalent card) {
        super(card);
    }

    @Override
    public BuildersTalent copy() {
        return new BuildersTalent(this);
    }
}
