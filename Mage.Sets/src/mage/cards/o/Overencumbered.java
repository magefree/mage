package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.JunkToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class Overencumbered extends CardImpl {

    public Overencumbered(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant opponent
        TargetPlayer auraTarget = new TargetOpponent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Overencumbered enters the battlefield, enchanted opponent creates a Clue token, a Food token, and a Junk token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OverencumberedTokenEffect()));

        // At the beginning of combat on enchanted opponent's turn, that player may pay {1} for each artifact they control. If they don't, creatures can't attack this combat.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                TargetController.ENCHANTED, new OverencumberedEffect(), false
        );
        this.addAbility(ability);
    }

    private Overencumbered(final Overencumbered card) {
        super(card);
    }

    @Override
    public Overencumbered copy() {
        return new Overencumbered(this);
    }
}

class OverencumberedTokenEffect extends OneShotEffect {

    OverencumberedTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "enchanted opponent creates a Clue token, a Food token, and a Junk token.";
    }

    private OverencumberedTokenEffect(final OverencumberedTokenEffect effect) {
        super(effect);
    }

    @Override
    public OverencumberedTokenEffect copy() {
        return new OverencumberedTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent overencumbered = game.getPermanentOrLKIBattlefield(source.getSourceId());
        new ClueArtifactToken().putOntoBattlefield(1, game, source, overencumbered.getAttachedTo());
        new FoodToken().putOntoBattlefield(1, game, source, overencumbered.getAttachedTo());
        new JunkToken().putOntoBattlefield(1, game, source, overencumbered.getAttachedTo());
        return true;
    }

}

class OverencumberedEffect extends OneShotEffect {

    OverencumberedEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player may pay {1} for each artifact they control. "
                + "If they don't, creatures can't attack this combat.";
    }

    private OverencumberedEffect(final OverencumberedEffect effect) {
        super(effect);
    }

    @Override
    public OverencumberedEffect copy() {
        return new OverencumberedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player enchantedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (enchantedPlayer == null || controller == null) {
            return false;
        }
        int amount = game.getBattlefield().countAll(
                StaticFilters.FILTER_PERMANENT_ARTIFACT, enchantedPlayer.getId(), game
        );
        if (!new GenericManaCost(amount).pay(source, game, source, enchantedPlayer.getId(), false)) {
            game.addEffect(new CantAttackAllEffect(Duration.EndOfCombat, StaticFilters.FILTER_PERMANENT_CREATURES), source);
        }
        return true;
    }
}
