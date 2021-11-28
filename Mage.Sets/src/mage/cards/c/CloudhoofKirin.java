
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class CloudhoofKirin extends CardImpl {

    public CloudhoofKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIRIN);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, you may have target player put the top X cards of their library into their graveyard, where X is that spell's converted mana cost.
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new CloudhoofKirinEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CloudhoofKirin(final CloudhoofKirin card) {
        super(card);
    }

    @Override
    public CloudhoofKirin copy() {
        return new CloudhoofKirin(this);
    }
}

class CloudhoofKirinEffect extends OneShotEffect {

    CloudhoofKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "target player mill X cards, where X is that spell's mana value";
    }

    private CloudhoofKirinEffect(final CloudhoofKirinEffect effect) {
        super(effect);
    }

    @Override
    public CloudhoofKirinEffect copy() {
        return new CloudhoofKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Player targetPlayer = null;
            for (Target target : source.getTargets()) {
                if (target instanceof TargetPlayer) {
                    targetPlayer = game.getPlayer(target.getFirstTarget());
                }
            }
            int cmc = spell.getManaValue();
            if (targetPlayer != null && cmc > 0) {
                targetPlayer.millCards(cmc, source, game);
                return true;
            }
        }
        return false;
    }
}
