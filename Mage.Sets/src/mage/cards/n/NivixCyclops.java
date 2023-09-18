package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class NivixCyclops extends CardImpl {

    public NivixCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Nivix Cyclops gets +3/+0 until end of turn and can attack this turn as though it didn't have defender.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(3, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false
        );
        ability.addEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn, "and"));
        this.addAbility(ability);
    }

    private NivixCyclops(final NivixCyclops card) {
        super(card);
    }

    @Override
    public NivixCyclops copy() {
        return new NivixCyclops(this);
    }
}
