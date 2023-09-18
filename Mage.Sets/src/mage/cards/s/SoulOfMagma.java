

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class SoulOfMagma extends CardImpl {

    public SoulOfMagma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(1), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SoulOfMagma(final SoulOfMagma card) {
        super(card);
    }

    @Override
    public SoulOfMagma copy() {
        return new SoulOfMagma(this);
    }

}
