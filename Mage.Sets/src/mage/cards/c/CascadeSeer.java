package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CascadeSeer extends CardImpl {

    public CascadeSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Cascade Seer enters the battlefield, scry X, where X is the number of creatures in your party.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CascadeSeerEffect()).addHint(PartyCountHint.instance));
    }

    private CascadeSeer(final CascadeSeer card) {
        super(card);
    }

    @Override
    public CascadeSeer copy() {
        return new CascadeSeer(this);
    }
}

class CascadeSeerEffect extends OneShotEffect {

    CascadeSeerEffect() {
        super(Outcome.Benefit);
        staticText = "scry X, where X is the number of creatures in your party. " + PartyCount.getReminder();
    }

    private CascadeSeerEffect(final CascadeSeerEffect effect) {
        super(effect);
    }

    @Override
    public CascadeSeerEffect copy() {
        return new CascadeSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int partyCount = PartyCount.instance.calculate(game, source, this);
        return partyCount > 0 && player.scry(partyCount, source, game);
    }
}
