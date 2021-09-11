package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RottenReunion extends CardImpl {

    public RottenReunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Exile up to one target card from a graveyard. Create a 2/2 black Zombie creature token with decayed.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieDecayedToken()));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 1));

        // Flashback {1}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{1}{B}"), TimingRule.INSTANT));
    }

    private RottenReunion(final RottenReunion card) {
        super(card);
    }

    @Override
    public RottenReunion copy() {
        return new RottenReunion(this);
    }
}
