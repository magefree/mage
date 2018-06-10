
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new CloudhoofKirinEffect(), StaticFilters.SPIRIT_OR_ARCANE_CARD, true, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public CloudhoofKirin(final CloudhoofKirin card) {
        super(card);
    }

    @Override
    public CloudhoofKirin copy() {
        return new CloudhoofKirin(this);
    }
}

class CloudhoofKirinEffect extends OneShotEffect {

    public CloudhoofKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may have target player put the top X cards of their library into their graveyard, where X is that spell's converted mana cost";
    }

    public CloudhoofKirinEffect(final CloudhoofKirinEffect effect) {
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
            if (targetPlayer != null) {
                return targetPlayer.moveCards(targetPlayer.getLibrary().getTopCards(game, spell.getConvertedManaCost()), Zone.GRAVEYARD, source, game);
            }
        }
        return false;
    }
}
