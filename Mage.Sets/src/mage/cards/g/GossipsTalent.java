package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GossipsTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GossipsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever a creature you control enters, surveil 1.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_PERMANENT_A_CREATURE
        ));

        // {1}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{U}"));

        // Whenever you attack, target attacking creature with power 3 or less can't be blocked this turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new CantBeBlockedTargetEffect(), 1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {3}{U}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{U}"));

        // Whenever a creature you control deals combat damage to a player, you may exile it, then return it to the battlefield under its owner's control.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new DealsDamageToAPlayerAllTriggeredAbility(
                        new ExileThenReturnTargetEffect(false, false)
                                .setText("exile it, then return it to the battlefield under its owner's control"),
                        StaticFilters.FILTER_CONTROLLED_CREATURE, true, SetTargetPointer.PERMANENT, true
                ), 3
        )));
    }

    private GossipsTalent(final GossipsTalent card) {
        super(card);
    }

    @Override
    public GossipsTalent copy() {
        return new GossipsTalent(this);
    }
}
