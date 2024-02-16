package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class ElusiveSpellfist extends CardImpl {

    public ElusiveSpellfist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, Elusive Spellfist gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1,0,Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        Effect effect = new CantBeBlockedSourceEffect(Duration.EndOfTurn);
        effect.setText("and can't be blocked this turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ElusiveSpellfist(final ElusiveSpellfist card) {
        super(card);
    }

    @Override
    public ElusiveSpellfist copy() {
        return new ElusiveSpellfist(this);
    }
}
