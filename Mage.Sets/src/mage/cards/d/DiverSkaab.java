package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiverSkaab extends CardImpl {

    public DiverSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Diver Skaab exploits a creature, target creature's owner puts it on the top or bottom of their library.
        Ability ability = new ExploitCreatureTriggeredAbility(new DiverSkaabEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DiverSkaab(final DiverSkaab card) {
        super(card);
    }

    @Override
    public DiverSkaab copy() {
        return new DiverSkaab(this);
    }
}

class DiverSkaabEffect extends OneShotEffect {

    DiverSkaabEffect() {
        super(Outcome.Removal);
        staticText = "target creature's owner puts it on the top or bottom of their library";
    }

    private DiverSkaabEffect(final DiverSkaabEffect effect) {
        super(effect);
    }

    @Override
    public DiverSkaabEffect copy() {
        return new DiverSkaabEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.Detriment, "Put the targeted object on the top or bottom of your library?",
                "", "Top", "Bottom", source, game)) {
            return new PutOnLibraryTargetEffect(true).apply(game, source);
        }
        return new PutOnLibraryTargetEffect(false).apply(game, source);
    }
}
