package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public class MyrCustodian extends CardImpl {
    public MyrCustodian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.addSubType(SubType.MYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        //When Myr Custodian enters the battlefield, scry 2. Then each opponent may scry 1.
        EntersBattlefieldTriggeredAbility entersBattlefieldTriggeredAbility =
                new EntersBattlefieldTriggeredAbility(new ScryEffect(2,false));
        entersBattlefieldTriggeredAbility.addEffect(new MyrCustodianScryEffect());
        this.addAbility(entersBattlefieldTriggeredAbility);
    }

    private MyrCustodian(final MyrCustodian card) {
        super(card);
    }

    @Override
    public MyrCustodian copy() {
        return new MyrCustodian(this);
    }
}

class MyrCustodianScryEffect extends OneShotEffect {

    MyrCustodianScryEffect() {
        super(Outcome.Benefit);
        staticText = "Then each opponent may scry 1";
    }

    private MyrCustodianScryEffect(final MyrCustodianScryEffect effect) {
        super(effect);
    }

    @Override
    public MyrCustodianScryEffect copy() {
        return new MyrCustodianScryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null && player.chooseUse(outcome, "Scry 1?", source, game)) {
                player.scry(1, source, game);
            }
        }
        return true;
    }
}
