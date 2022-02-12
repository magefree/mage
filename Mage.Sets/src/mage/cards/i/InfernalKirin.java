package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InfernalKirin extends CardImpl {

    public InfernalKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIRIN);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Spirit or Arcane spell, target player reveals their hand and discards all cards with that spell's converted mana cost.
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new InfernalKirinEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private InfernalKirin(final InfernalKirin card) {
        super(card);
    }

    @Override
    public InfernalKirin copy() {
        return new InfernalKirin(this);
    }
}

class InfernalKirinEffect extends OneShotEffect {

    InfernalKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "target player reveals their hand and discards all cards with that spell's mana value";
    }

    private InfernalKirinEffect(final InfernalKirinEffect effect) {
        super(effect);
    }

    @Override
    public InfernalKirinEffect copy() {
        return new InfernalKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        int cmc = spell.getManaValue();
        Player targetPlayer = null;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetPlayer) {
                targetPlayer = game.getPlayer(target.getFirstTarget());
            }
        }
        if (targetPlayer == null) {
            return false;
        }
        if (targetPlayer.getHand().isEmpty()) {
            return true;
        }
        targetPlayer.revealCards(source, targetPlayer.getHand(), game);
        Cards cards = targetPlayer.getHand().copy();
        cards.removeIf(uuid -> game.getCard(uuid).getManaValue() != cmc);
        targetPlayer.discard(cards, false, source, game);
        return true;
    }
}
