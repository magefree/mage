package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class LobeLobber extends CardImpl {

    public LobeLobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "T: This creature deals 1 damage to target player or planeswalker. Roll a six-sided die. On a 5 or higher, untap it."
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        ability.addEffect(new LobeLobberEffect());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));

        // Equip 2
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
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

    LobeLobberEffect() {
        super(Outcome.Benefit);
        this.staticText = "Roll a six-sided die. On a 5 or higher, untap it";
    }

    private LobeLobberEffect(final LobeLobberEffect effect) {
        super(effect);
    }

    @Override
    public LobeLobberEffect copy() {
        return new LobeLobberEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            if (amount >= 5) {
                new UntapSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }
}
