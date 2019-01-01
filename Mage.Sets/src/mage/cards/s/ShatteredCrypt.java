
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class ShatteredCrypt extends CardImpl {

    public ShatteredCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Return X target creature cards from your graveyard to your hand. You lose X life.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target creature cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(1, new FilterCreatureCard()));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            Target target = new TargetCardInYourGraveyard(xValue, new FilterCreatureCard((xValue != 1 ? " creature cards" : "creature card") + " from your graveyard"));
            ability.addTarget(target);
        }
    }

    public ShatteredCrypt(final ShatteredCrypt card) {
        super(card);
    }

    @Override
    public ShatteredCrypt copy() {
        return new ShatteredCrypt(this);
    }
}
