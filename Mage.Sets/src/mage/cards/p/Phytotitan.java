
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class Phytotitan extends CardImpl {

    public Phytotitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(2);

        // When Phytotitan dies, return it to the battlefield tapped under its owner's control at the beginning of their next upkeep.
        this.addAbility(new DiesSourceTriggeredAbility(new PhytotitanEffect()));
    }

    private Phytotitan(final Phytotitan card) {
        super(card);
    }

    @Override
    public Phytotitan copy() {
        return new Phytotitan(this);
    }
}

class PhytotitanEffect extends OneShotEffect {

    private static final String effectText = "return it to the battlefield tapped under its owner's control at the beginning of their next upkeep";

    PhytotitanEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    private PhytotitanEffect(final PhytotitanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //create delayed triggered ability
        Effect effect = new ReturnSourceFromGraveyardToBattlefieldEffect(true, true);
        effect.setText(staticText);
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }

    @Override
    public PhytotitanEffect copy() {
        return new PhytotitanEffect(this);
    }

}
