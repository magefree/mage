
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class ResoundingScream extends CardImpl {

    public ResoundingScream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Target player discards a card at random.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Cycling {5}{U}{B}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{5}{U}{B}{R}")));
        // When you cycle Resounding Scream, target player discards two cards at random.
        Ability ability = new CycleTriggeredAbility(new DiscardTargetEffect(2, true));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ResoundingScream(final ResoundingScream card) {
        super(card);
    }

    @Override
    public ResoundingScream copy() {
        return new ResoundingScream(this);
    }
}
