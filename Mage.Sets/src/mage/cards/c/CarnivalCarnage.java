package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarnivalCarnage extends SplitCard {

    public CarnivalCarnage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{B/R}", "{2}{B}{R}", SpellAbilityType.SPLIT);

        // Carnival
        // Carnival deals 1 damage to target creature or planeswalker and 1 damage to that permanent's controller.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CarnivalEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // Carnage
        // Carnage deals 3 damage to target opponent. That player discards two cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getRightHalfCard().getSpellAbility().addEffect(
                new DiscardTargetEffect(2).setText("That player discards two cards.")
        );
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());
    }

    private CarnivalCarnage(final CarnivalCarnage card) {
        super(card);
    }

    @Override
    public CarnivalCarnage copy() {
        return new CarnivalCarnage(this);
    }
}

class CarnivalEffect extends OneShotEffect {

    CarnivalEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to target creature or planeswalker " +
                "and 1 damage to that permanent's controller";
    }

    private CarnivalEffect(final CarnivalEffect effect) {
        super(effect);
    }

    @Override
    public CarnivalEffect copy() {
        return new CarnivalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(1, source.getSourceId(), source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(1, source.getSourceId(), source, game);
        }
        return true;
    }
}