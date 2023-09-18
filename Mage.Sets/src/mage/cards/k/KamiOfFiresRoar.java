

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
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
public final class KamiOfFiresRoar extends CardImpl {

    public KamiOfFiresRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a Spirit or Arcane spell, target creature can't block this turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KamiOfFiresRoar(final KamiOfFiresRoar card) {
        super(card);
    }

    @Override
    public KamiOfFiresRoar copy() {
        return new KamiOfFiresRoar(this);
    }

}
