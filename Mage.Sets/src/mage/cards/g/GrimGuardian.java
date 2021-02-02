
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GrimGuardian extends CardImpl {

    public GrimGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Constellation - Whenever Grim Guardian or another enchantment enters the battlefield under your control, each opponent loses 1 life.
        this.addAbility(new ConstellationAbility(new LoseLifeOpponentsEffect(1), false));
    }

    private GrimGuardian(final GrimGuardian card) {
        super(card);
    }

    @Override
    public GrimGuardian copy() {
        return new GrimGuardian(this);
    }
}
