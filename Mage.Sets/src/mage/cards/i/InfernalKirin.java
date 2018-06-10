
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;

/**
 *
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
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new InfernalKirinEffect(), StaticFilters.SPIRIT_OR_ARCANE_CARD, false, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public InfernalKirin(final InfernalKirin card) {
        super(card);
    }

    @Override
    public InfernalKirin copy() {
        return new InfernalKirin(this);
    }
}

class InfernalKirinEffect extends OneShotEffect {

    public InfernalKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "target player reveals their hand and discards all cards with that spell's converted mana cost";
    }

    public InfernalKirinEffect(final InfernalKirinEffect effect) {
        super(effect);
    }

    @Override
    public InfernalKirinEffect copy() {
        return new InfernalKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            int cmc = spell.getConvertedManaCost();
            Player targetPlayer = null;
            for (Target target : source.getTargets()) {
                if (target instanceof TargetPlayer) {
                    targetPlayer = game.getPlayer(target.getFirstTarget());
                }
            }
            if (targetPlayer != null) {
                if (!targetPlayer.getHand().isEmpty()) {
                    targetPlayer.revealCards("Infernal Kirin", targetPlayer.getHand(), game);
                    for (UUID uuid : targetPlayer.getHand().copy()) {
                        Card card = game.getCard(uuid);
                        if (card != null && card.getConvertedManaCost() == cmc) {
                            targetPlayer.discard(card, source, game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
