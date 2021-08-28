package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
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
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RangerClass extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("cast creature spells");

    public RangerClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Ranger Class enters the battlefield, create a 2/2 green Wolf creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken())));

        // {1}{G}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{G}"));

        // Whenever you attack, put a +1/+1 counter on target attacking creature.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 0
        );
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {3}{G}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{G}"));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new LookAtTopCardOfLibraryAnyTimeEffect(), 3
        )));

        // You may cast creature spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new PlayTheTopCardEffect(TargetController.YOU, filter, false), 3
        )));
    }

    private RangerClass(final RangerClass card) {
        super(card);
    }

    @Override
    public RangerClass copy() {
        return new RangerClass(this);
    }
}
