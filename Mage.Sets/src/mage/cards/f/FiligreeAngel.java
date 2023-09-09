package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Loki
 */
public final class FiligreeAngel extends CardImpl {

    public FiligreeAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{W}{W}{U}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Filigree Angel enters the battlefield, you gain 3 life for each artifact you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FiligreeAngelEffect()).addHint(ArtifactYouControlHint.instance));
    }

    private FiligreeAngel(final FiligreeAngel card) {
        super(card);
    }

    @Override
    public FiligreeAngel copy() {
        return new FiligreeAngel(this);
    }
}

class FiligreeAngelEffect extends OneShotEffect {

    public FiligreeAngelEffect() {
        super(Outcome.GainLife);
        staticText = "you gain 3 life for each artifact you control";
    }

    private FiligreeAngelEffect(final FiligreeAngelEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int life = ArtifactYouControlCount.instance.calculate(game, source, this) * 3;
            player.gainLife(life, game, source);
        }
        return true;
    }

    @Override
    public FiligreeAngelEffect copy() {
        return new FiligreeAngelEffect(this);
    }

}
