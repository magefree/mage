
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author anonymous
 */
public final class RancidEarth extends CardImpl {

    public RancidEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Destroy target land.
        // Threshold - If seven or more cards are in your graveyard, instead destroy that land and Rancid Earth deals 1 damage to each creature and each player.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RancidEarthEffect(),
                new DestroyTargetEffect(),
                new CardsInControllerGraveyardCondition(7),
                "Destroy target land.<br/><br/><i>Threshold</i> &mdash; If seven or more cards are in your graveyard, instead destroy that land and Rancid Earth deals 1 damage to each creature and each player."));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private RancidEarth(final RancidEarth card) {
        super(card);
    }

    @Override
    public RancidEarth copy() {
        return new RancidEarth(this);
    }
}

class RancidEarthEffect extends OneShotEffect {

    public RancidEarthEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy that land and Rancid Earth deals 1 damage to each creature and each player";
    }

    private RancidEarthEffect(final RancidEarthEffect effect) {
        super(effect);
    }

    @Override
    public RancidEarthEffect copy() {
        return new RancidEarthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect1 = new DestroyTargetEffect("destroy that land");
        effect1.apply(game, source);
        return new DamageEverythingEffect(1).apply(game, source);
    }
}
