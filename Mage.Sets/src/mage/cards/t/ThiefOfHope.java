

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 * @author Loki
 */
public final class ThiefOfHope extends CardImpl {

    public ThiefOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SpellCastControllerTriggeredAbility(new LoseLifeTargetEffect(1), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        this.addAbility(new SoulshiftAbility(2));
    }

    private ThiefOfHope(final ThiefOfHope card) {
        super(card);
    }

    @Override
    public ThiefOfHope copy() {
        return new ThiefOfHope(this);
    }

}
