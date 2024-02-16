
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AlchemistsGreeting extends CardImpl {

    public AlchemistsGreeting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");

        // Alchemist's Greeting deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Madness {1}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    private AlchemistsGreeting(final AlchemistsGreeting card) {
        super(card);
    }

    @Override
    public AlchemistsGreeting copy() {
        return new AlchemistsGreeting(this);
    }
}
