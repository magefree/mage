package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class AkkiCoalflinger extends CardImpl {

    public AkkiCoalflinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FirstStrikeAbility.getInstance());
        Effect effect = new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES);
        Ability ability = new SimpleActivatedAbility(effect, new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AkkiCoalflinger(final AkkiCoalflinger card) {
        super(card);
    }

    @Override
    public AkkiCoalflinger copy() {
        return new AkkiCoalflinger(this);
    }

}
