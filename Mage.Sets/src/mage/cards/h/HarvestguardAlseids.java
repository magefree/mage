
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HarvestguardAlseids extends CardImpl {

    public HarvestguardAlseids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.NYMPH);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Constellation — Whenever Harvestguard Alseids or another enchantment enters the battlefield under your control, prevent all damage that would be dealt to target creature this turn.
        Ability ability = new ConstellationAbility(new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HarvestguardAlseids(final HarvestguardAlseids card) {
        super(card);
    }

    @Override
    public HarvestguardAlseids copy() {
        return new HarvestguardAlseids(this);
    }
}
