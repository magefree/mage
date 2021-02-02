
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
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
public final class WhitewaterNaiads extends CardImpl {

    public WhitewaterNaiads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.NYMPH);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Constellation - Whenever Whitewater Naiads or another enchantment enters the battlefield under your control, target creature can't be blocked this turn.
        Ability ability = new ConstellationAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());        
        this.addAbility(ability);
    }

    private WhitewaterNaiads(final WhitewaterNaiads card) {
        super(card);
    }

    @Override
    public WhitewaterNaiads copy() {
        return new WhitewaterNaiads(this);
    }
}
