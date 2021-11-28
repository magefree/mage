

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class OrbweaverKumo extends CardImpl {

    public OrbweaverKumo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(new ForestwalkAbility(), Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private OrbweaverKumo(final OrbweaverKumo card) {
        super(card);
    }

    @Override
    public OrbweaverKumo copy() {
        return new OrbweaverKumo(this);
    }

}
