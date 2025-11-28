package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class DaybreakRanger extends TransformingDoubleFacedCard {

    public DaybreakRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ARCHER, SubType.RANGER, SubType.WEREWOLF}, "{2}{G}",
                "Nightfall Predator",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        // Daybreak Ranger
        this.getLeftHalfCard().setPT(2, 2);

        // {T}: Daybreak Ranger deals 2 damage to target creature with flying.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Daybreak Ranger.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Nightfall Predator
        this.getRightHalfCard().setPT(4, 4);

        // {R}, {T}: Nightfall Predator fights target creature.
        Ability ability2 = new SimpleActivatedAbility(
                new FightTargetSourceEffect().setText("{this} fights target creature. <i>(Each deals damage equal to its power to the other.)</i>"),
                new ManaCostsImpl<>("{R}")
        );
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().addAbility(ability2);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Nightfall Predator.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private DaybreakRanger(final DaybreakRanger card) {
        super(card);
    }

    @Override
    public DaybreakRanger copy() {
        return new DaybreakRanger(this);
    }
}
