
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author L_J
 */
public final class BlastFromThePast extends CardImpl {

    public BlastFromThePast (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Madness {R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{R}")));
        // Cycling {1}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{R}")));
        // Kicker {2}{R}
        this.addAbility(new KickerAbility("{2}{R}"));
        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}")));
        // Buyback {4}{R}
        this.addAbility(new BuybackAbility("{4}{R}"));

        // Blast from the Past deals 2 damage to any target. If this spell was kicked, create a 1/1 red Goblin creature token.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new GoblinToken()), KickedCondition.instance));
    }

    public BlastFromThePast (final BlastFromThePast card) {
        super(card);
    }

    @Override
    public BlastFromThePast copy() {
        return new BlastFromThePast(this);
    }

}
