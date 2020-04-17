package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlisterspitGremlin extends CardImpl {

    public BlisterspitGremlin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, {T}: Blisterspit Gremlin deals 1 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, untap Blisterspit Gremlin.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new UntapSourceEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private BlisterspitGremlin(final BlisterspitGremlin card) {
        super(card);
    }

    @Override
    public BlisterspitGremlin copy() {
        return new BlisterspitGremlin(this);
    }
}
