package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrGwynHeroOfAshvale extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("equipped creature you control");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final FilterControlledCreaturePermanent filter3
            = new FilterControlledCreaturePermanent(SubType.KNIGHT, "Knight");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public SyrGwynHeroOfAshvale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever an equipped creature you control attacks, you draw a card and you lose 1 life.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card"), false, filter
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Equipment you control have equip Knight {0}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new EquipAbility(
                        Outcome.AddAbility, new GenericManaCost(0),
                        new TargetControlledCreaturePermanent(filter3)
                ), Duration.WhileOnBattlefield, filter2
        ).setText("Equipment you control have equip Knight {0}.")));
    }

    private SyrGwynHeroOfAshvale(final SyrGwynHeroOfAshvale card) {
        super(card);
    }

    @Override
    public SyrGwynHeroOfAshvale copy() {
        return new SyrGwynHeroOfAshvale(this);
    }
}
