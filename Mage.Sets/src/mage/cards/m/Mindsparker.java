
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Mindsparker extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("white or blue instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)));
    }

    public Mindsparker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever an opponent casts a white or blue instant or sorcery spell, Mindsparker deals 2 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new MindsparkerEffect(), filter, false, SetTargetPointer.PLAYER));

    }

    public Mindsparker(final Mindsparker card) {
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
            targetPlayer.damage(2, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
