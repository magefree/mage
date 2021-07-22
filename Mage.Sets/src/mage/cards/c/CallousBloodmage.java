package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Pest11GainLifeToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallousBloodmage extends CardImpl {

    public CallousBloodmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Callous Bloodmage enters the battlefield, choose one —
        // • Create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Pest11GainLifeToken()));

        // • You draw a card and you lose 1 life.
        Mode mode = new Mode(new DrawCardSourceControllerEffect(1).setText("you draw a card"));
        mode.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        ability.addMode(mode);

        // • Exile target player's graveyard.
        mode = new Mode(new ExileGraveyardAllTargetPlayerEffect().setText("exile target player's graveyard"));
        mode.addTarget(new TargetPlayer());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private CallousBloodmage(final CallousBloodmage card) {
        super(card);
    }

    @Override
    public CallousBloodmage copy() {
        return new CallousBloodmage(this);
    }
}
