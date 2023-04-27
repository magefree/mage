package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TorrentElemental extends CardImpl {

    public TorrentElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Torrent Elemental attacks, tap all creatures defending player controls.
        Effect effect = new TapAllTargetPlayerControlsEffect(FILTER_PERMANENT_CREATURES);
        effect.setText("tap all creatures defending player controls.");
        this.addAbility(new AttacksTriggeredAbility(effect, false, null, SetTargetPointer.PLAYER));
        // {3}{B/G}{B/G}: Put Torrent Elemental from exile onto the battlefield tapped. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.EXILED, new ReturnSourceFromExileToBattlefieldEffect(true), new ManaCostsImpl<>("{3}{B/G}{B/G}"));
        this.addAbility(ability);

    }

    private TorrentElemental(final TorrentElemental card) {
        super(card);
    }

    @Override
    public TorrentElemental copy() {
        return new TorrentElemental(this);
    }
}

class ReturnSourceFromExileToBattlefieldEffect extends OneShotEffect {

    private boolean tapped;
    private boolean ownerControl;

    public ReturnSourceFromExileToBattlefieldEffect() {
        this(false);
    }

    public ReturnSourceFromExileToBattlefieldEffect(boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        setText();
    }

    public ReturnSourceFromExileToBattlefieldEffect(boolean tapped, boolean ownerControl) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        this.ownerControl = ownerControl;
        setText();
    }

    public ReturnSourceFromExileToBattlefieldEffect(final ReturnSourceFromExileToBattlefieldEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.ownerControl = effect.ownerControl;
    }

    @Override
    public ReturnSourceFromExileToBattlefieldEffect copy() {
        return new ReturnSourceFromExileToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getState().getZone(source.getSourceId()) != Zone.EXILED) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        Player player;
        if (ownerControl) {
            player = game.getPlayer(card.getOwnerId());
        } else {
            player = game.getPlayer(source.getControllerId());
        }
        if (player == null) {
            return false;
        }

        return player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("Put {this} from exile onto the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (ownerControl) {
            sb.append(" under its owner's control");
        }
        staticText = sb.toString();
    }

}
