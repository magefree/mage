
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class Probe extends CardImpl {

    public Probe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Kicker {1}{B}
        this.addAbility(new KickerAbility("{1}{B}"));
        // Draw three cards, then discard two cards.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 2));
        // If Probe was kicked, target player discards two cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardTargetEffect(2),
                KickedCondition.instance,
                "<br><br>if this spell was kicked, target player discards two cards"));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            if (KickedCondition.instance.apply(game, ability)) {
                ability.addTarget(new TargetPlayer());
            }
        }
    }

    public Probe(final Probe card) {
        super(card);
    }

    @Override
    public Probe copy() {
        return new Probe(this);
    }
}
