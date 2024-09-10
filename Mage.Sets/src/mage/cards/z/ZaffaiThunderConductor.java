package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.Elemental44Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZaffaiThunderConductor extends CardImpl {

    public ZaffaiThunderConductor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, scry 1. If that spell's mana value is 5 or greater, create a 4/4 blue and red Elemental creature token. If that spell's mana value is 10 or greater, Zaffai, Thunder Conductor deals 10 damage to an opponent chosen at random.
        this.addAbility(new MagecraftAbility(new ZaffaiThunderConductorEffect()));
    }

    private ZaffaiThunderConductor(final ZaffaiThunderConductor card) {
        super(card);
    }

    @Override
    public ZaffaiThunderConductor copy() {
        return new ZaffaiThunderConductor(this);
    }
}

class ZaffaiThunderConductorEffect extends OneShotEffect {

    ZaffaiThunderConductorEffect() {
        super(Outcome.Benefit);
        staticText = "scry 1. If that spell's mana value is 5 or greater, " +
                "create a 4/4 blue and red Elemental creature token. If that spell's mana value is 10 or greater, " +
                "{this} deals 10 damage to an opponent chosen at random";
    }

    private ZaffaiThunderConductorEffect(final ZaffaiThunderConductorEffect effect) {
        super(effect);
    }

    @Override
    public ZaffaiThunderConductorEffect copy() {
        return new ZaffaiThunderConductorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        player.scry(1, source, game);
        Spell spell = (Spell) getValue(MagecraftAbility.SPELL_KEY);
        if (spell == null || spell.getManaValue() < 5) {
            return true;
        }

        new Elemental44Token().putOntoBattlefield(1, game, source, source.getControllerId());
        if (spell.getManaValue() < 10) {
            return true;
        }

        TargetOpponent target = new TargetOpponent(true);
        target.setRandom(true);
        target.chooseTarget(Outcome.Damage, player.getId(), source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent != null) {
            opponent.damage(10, source.getSourceId(), source, game);
        }
        return true;
    }
}
