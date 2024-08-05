package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OrochiSoulReaver extends CardImpl {

    public OrochiSoulReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ninjutsu {3}{B}
        this.addAbility(new NinjutsuAbility("{3}{B}"));

        // Whenever one or more creatures you control deal combat damage to a player, create a Treasure token and manifest the top card of that player's library.
        Ability ability = new DealCombatDamageControlledTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()),
                SetTargetPointer.PLAYER
        );
        ability.addEffect(new OrochiSoulReaverManifestEffect().concatBy("and"));
        this.addAbility(ability);
    }

    private OrochiSoulReaver(final OrochiSoulReaver card) {
        super(card);
    }

    @Override
    public OrochiSoulReaver copy() {
        return new OrochiSoulReaver(this);
    }
}

class OrochiSoulReaverManifestEffect extends OneShotEffect {

    OrochiSoulReaverManifestEffect() {
        super(Outcome.Benefit);
        staticText = "manifest the top card of that player's library";
    }

    private OrochiSoulReaverManifestEffect(final OrochiSoulReaverManifestEffect effect) {
        super(effect);
    }

    @Override
    public OrochiSoulReaverManifestEffect copy() {
        return new OrochiSoulReaverManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }

        return !ManifestEffect.doManifestCards(game, source, controller, targetPlayer.getLibrary().getTopCards(game, 1)).isEmpty();
    }
}
