package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class SoulbladeDjinn extends CardImpl {

    public SoulbladeDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast a noncreature spell, creatures you control get +1/+1 until end of turn.
        Effect effect = new BoostControlledEffect(1,1,Duration.EndOfTurn);
        Ability ability = new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        this.addAbility(ability);
    }

    private SoulbladeDjinn(final SoulbladeDjinn card) {
        super(card);
    }

    @Override
    public SoulbladeDjinn copy() {
        return new SoulbladeDjinn(this);
    }
}
