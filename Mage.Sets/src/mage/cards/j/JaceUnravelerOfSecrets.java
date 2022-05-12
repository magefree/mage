
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.JaceUnravelerOfSecretsEmblem;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

/**
 * import mage.game.command.emblems.JaceUnravelerOfSecretsEmblem;
 *
 * @author LevelX2
 */
public final class JaceUnravelerOfSecrets extends CardImpl {

    public JaceUnravelerOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.setStartingLoyalty(5);

        // +1: Scry 1, then draw a card.
        Ability ability = new LoyaltyAbility(new ScryEffect(1, false), 1);
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText(", then draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -2: Return target creature to its owner's hand.
        ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -8: You get an emblem with "Whenever an opponent casts their first spell each turn, counter that spell."
        LoyaltyAbility ability2 = new LoyaltyAbility(new GetEmblemEffect(new JaceUnravelerOfSecretsEmblem()), -8);
        this.addAbility(ability2, new SpellsCastWatcher());

    }

    private JaceUnravelerOfSecrets(final JaceUnravelerOfSecrets card) {
        super(card);
    }

    @Override
    public JaceUnravelerOfSecrets copy() {
        return new JaceUnravelerOfSecrets(this);
    }
}
