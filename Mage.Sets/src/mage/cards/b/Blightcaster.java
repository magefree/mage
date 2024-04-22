package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Blightcaster extends CardImpl {

    public Blightcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast an enchantment spell, you may have target creature get -2/-2 until end of turn.
        Effect effect = new BoostTargetEffect(-2,-2, Duration.EndOfTurn);
        Ability ability = new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private Blightcaster(final Blightcaster card) {
        super(card);
    }

    @Override
    public Blightcaster copy() {
        return new Blightcaster(this);
    }
}
