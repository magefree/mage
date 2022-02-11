package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class LobeLobber extends CardImpl {

    public LobeLobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "T: This creature deals 1 damage to target player. Roll a six-sided die. On a 5 or higher, untap it."
        Effect effect = new LobeLobberEffect();
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));

        // Equip 2
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private LobeLobber(final LobeLobber card) {
        super(card);
    }

    @Override
    public LobeLobber copy() {
        return new LobeLobber(this);
    }
}

class LobeLobberEffect extends OneShotEffect {

    public LobeLobberEffect() {
        super(Outcome.Benefit);
        this.staticText = "This creature deals 1 damage to target player. Roll a six-sided die. On a 5 or higher, untap it";
    }

    public LobeLobberEffect(final LobeLobberEffect effect) {
        super(effect);
    }

    @Override
    public LobeLobberEffect copy() {
        return new LobeLobberEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent equipment = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getFirstTarget());

        if (controller != null && equipment != null && player != null) {
            player.damage(1, source.getSourceId(), source, game);
            int amount = controller.rollDice(outcome, source, game, 6);
            if (amount >= 5) {
                new UntapSourceEffect().apply(game, source);
            }
            return true;
        }

        return false;
    }
}
