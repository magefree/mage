package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KindlesparkDuo extends CardImpl {

    public KindlesparkDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Kindlespark Duo deals 1 damage to target opponent.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, untap Kindlespark Duo.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new UntapSourceEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private KindlesparkDuo(final KindlesparkDuo card) {
        super(card);
    }

    @Override
    public KindlesparkDuo copy() {
        return new KindlesparkDuo(this);
    }
}
