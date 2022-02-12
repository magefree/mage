

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class KamiOfTheWaningMoon extends CardImpl {

    public KamiOfTheWaningMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new SpellCastControllerTriggeredAbility(new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KamiOfTheWaningMoon(final KamiOfTheWaningMoon card) {
        super(card);
    }

    @Override
    public KamiOfTheWaningMoon copy() {
        return new KamiOfTheWaningMoon(this);
    }

}
