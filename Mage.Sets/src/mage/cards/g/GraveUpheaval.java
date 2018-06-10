
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class GraveUpheaval extends CardImpl {

    public GraveUpheaval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{R}");

        // Put target creature card from a graveyard onto the battlefield under your control. It gains haste.
        this.getSpellAbility().addEffect(new GraveUpheavalEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl("{2}")));
    }

    public GraveUpheaval(final GraveUpheaval card) {
        super(card);
    }

    @Override
    public GraveUpheaval copy() {
        return new GraveUpheaval(this);
    }
}

class GraveUpheavalEffect extends OneShotEffect {

    public GraveUpheavalEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put target creature card from a graveyard onto the battlefield under your control. It gains haste";
    }

    public GraveUpheavalEffect(final GraveUpheavalEffect effect) {
        super(effect);
    }

    @Override
    public GraveUpheavalEffect copy() {
        return new GraveUpheavalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent != null) {
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }

        return false;
    }
}
