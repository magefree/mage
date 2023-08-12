package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Mindsparker extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white or blue instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)));
    }

    public Mindsparker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever an opponent casts a white or blue instant or sorcery spell, Mindsparker deals 2 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new MindsparkerEffect(), filter, false, SetTargetPointer.PLAYER));

    }

    private Mindsparker(final Mindsparker card) {
        super(card);
    }

    @Override
    public Mindsparker copy() {
        return new Mindsparker(this);
    }
}

class MindsparkerEffect extends OneShotEffect {

    public MindsparkerEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to that player";
    }

    public MindsparkerEffect(final MindsparkerEffect effect) {
        super(effect);
    }

    @Override
    public MindsparkerEffect copy() {
        return new MindsparkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(2, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}
