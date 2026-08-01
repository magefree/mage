package mage.cards.k;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author muz
 */
public final class KimoyoBeads extends CardImpl {

    public KimoyoBeads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your end step, choose one that hasn't been chosen --
        // * AV Bead -- Draw a card.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
            new DrawCardSourceControllerEffect(1)
        );
        ability.getModes().getMode().withFlavorWord("AV Bead");
        ability.setModeTag("draw a card");
        ability.getModes().setLimitUsageByOnce(false);

        // * Communication Bead -- Create two 1/1 white Soldier creature tokens.
        ability.addMode(new Mode(new CreateTokenEffect(new SoldierToken(), 2))
            .withFlavorWord("Communication Bead")
            .setModeTag("create soldier tokens")
        );

        // * Prime Bead -- You gain 3 life. Exile this artifact, then return it to the battlefield under its owner's control.
        ability.addMode(new Mode(new GainLifeEffect(3))
            .addEffect(new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD)
                .setText("exile this artifact, then return it to the battlefield under its owner's control"))
            .withFlavorWord("Prime Bead")
            .setModeTag("gain life, and exile then return"));

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private KimoyoBeads(final KimoyoBeads card) {
        super(card);
    }

    @Override
    public KimoyoBeads copy() {
        return new KimoyoBeads(this);
    }
}
