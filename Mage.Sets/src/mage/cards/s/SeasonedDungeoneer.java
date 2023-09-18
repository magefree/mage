package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeasonedDungeoneer extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creatures");
    private static final FilterPermanent filter2
            = new FilterAttackingCreature("attacking Cleric, Rogue, Warrior, or Wizard");

    static {
        filter2.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.ROGUE.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    public SeasonedDungeoneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Seasoned Dungeoneer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Whenever you attack, target attacking Cleric, Rogue, Warrior, or Wizard gains protection from creatures until end of turn. It explores.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new GainAbilityTargetEffect(new ProtectionAbility(filter)), 1
        );
        ability.addEffect(new ExploreTargetEffect().setText("It explores"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private SeasonedDungeoneer(final SeasonedDungeoneer card) {
        super(card);
    }

    @Override
    public SeasonedDungeoneer copy() {
        return new SeasonedDungeoneer(this);
    }
}
