package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KaervekTheMerciless extends CardImpl {

    public KaervekTheMerciless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever an opponent casts a spell, Kaervek the Merciless deals damage to any target equal to that spell's converted mana cost.
        Ability ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new KaervekTheMercilessEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private KaervekTheMerciless(final KaervekTheMerciless card) {
        super(card);
    }

    @Override
    public KaervekTheMerciless copy() {
        return new KaervekTheMerciless(this);
    }
}

class KaervekTheMercilessEffect extends OneShotEffect {

    public KaervekTheMercilessEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage equal to that spell's mana value to any target";
    }

    public KaervekTheMercilessEffect(final KaervekTheMercilessEffect effect) {
        super(effect);
    }

    @Override
    public KaervekTheMercilessEffect copy() {
        return new KaervekTheMercilessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            int cost = spell.getManaValue();
            Player target = game.getPlayer(source.getFirstTarget());
            if (target != null) {
                target.damage(cost, source.getSourceId(), source, game);
                return true;
            }
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(cost, source.getSourceId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}
