

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class KodamaOfTheSouthTree extends CardImpl {

    public KodamaOfTheSouthTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, true), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, true));
        this.addAbility(ability);
    }

    private KodamaOfTheSouthTree(final KodamaOfTheSouthTree card) {
        super(card);
    }

    @Override
    public KodamaOfTheSouthTree copy() {
        return new KodamaOfTheSouthTree(this);
    }

}
