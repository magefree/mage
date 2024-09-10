package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentNoManaSpentTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class BoromirWardenOfTheTower extends CardImpl {

    public BoromirWardenOfTheTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an opponent casts a spell, if no mana was spent to cast it, counter that spell.
        this.addAbility(new SpellCastOpponentNoManaSpentTriggeredAbility(new CounterTargetEffect().setText("counter that spell")));

        // Sacrifice Boromir, Warden of the Tower: Creatures you control gain indestructible until end of turn. The Ring tempts you.
        Ability ability = new SimpleActivatedAbility(new GainAbilityAllEffect(
            IndestructibleAbility.getInstance(), Duration.EndOfTurn,
            StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ), new SacrificeSourceCost());
        ability.addEffect(new TheRingTemptsYouEffect());
        this.addAbility(ability);
    }

    private BoromirWardenOfTheTower(final BoromirWardenOfTheTower card) {
        super(card);
    }

    @Override
    public BoromirWardenOfTheTower copy() {
        return new BoromirWardenOfTheTower(this);
    }
}
