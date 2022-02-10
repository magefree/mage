
package mage.cards.d;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.command.emblems.DackFaydenEmblem;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author emerald000
 */
public final class DackFayden extends CardImpl {

    public DackFayden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DACK);

        this.setStartingLoyalty(3);

        // +1: Target player draws two cards, then discards two cards.
        LoyaltyAbility ability = new LoyaltyAbility(new DrawCardTargetEffect(2), 1);
        Effect effect = new DiscardTargetEffect(2);
        effect.setText(", then discards two cards");
        ability.addEffect(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -2: Gain control of target artifact.
        effect = new GainControlTargetEffect(Duration.EndOfGame, true);
        effect.setText("Gain control of target artifact");
        ability = new LoyaltyAbility(effect, -2);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // -6: You get an emblem with "Whenever you cast a spell that targets one or more permanents, gain control of those permanents."
        effect = new GetEmblemEffect(new DackFaydenEmblem());
        effect.setText("You get an emblem with \"Whenever you cast a spell that targets one or more permanents, gain control of those permanents.\"");
        ability = new LoyaltyAbility(effect, -6);
        this.addAbility(ability);
    }

    private DackFayden(final DackFayden card) {
        super(card);
    }

    @Override
    public DackFayden copy() {
        return new DackFayden(this);
    }
}
