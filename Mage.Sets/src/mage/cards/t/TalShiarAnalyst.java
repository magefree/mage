package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
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
 * @author muz
 */
public final class TalShiarAnalyst extends CardImpl {

    public TalShiarAnalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ROMULAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {T}: This creature deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(
            new DamagePlayersEffect(1, TargetController.OPPONENT),
            new TapSourceCost()
        ));

        // Whenever you cast a noncreature spell, untap this creature.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new UntapSourceEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private TalShiarAnalyst(final TalShiarAnalyst card) {
        super(card);
    }

    @Override
    public TalShiarAnalyst copy() {
        return new TalShiarAnalyst(this);
    }
}
