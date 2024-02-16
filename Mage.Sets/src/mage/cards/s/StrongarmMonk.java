package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class StrongarmMonk extends CardImpl {

    public StrongarmMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, creatures you control get +1/+1 until end of turn.
        Effect effect = new BoostControlledEffect(1,1,Duration.EndOfTurn);
        Ability ability = new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        this.addAbility(ability);
    }

    private StrongarmMonk(final StrongarmMonk card) {
        super(card);
    }

    @Override
    public StrongarmMonk copy() {
        return new StrongarmMonk(this);
    }
}
