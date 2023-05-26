package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrarkTheThumbless extends CardImpl {

    public KrarkTheThumbless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell, flip a coin. If you lose the flip, return that spell to its owner's hand. If you win the flip, copy that spell, and you may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new KrarkTheThumblessEffect(),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false, true
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KrarkTheThumbless(final KrarkTheThumbless card) {
        super(card);
    }

    @Override
    public KrarkTheThumbless copy() {
        return new KrarkTheThumbless(this);
    }
}

class KrarkTheThumblessEffect extends OneShotEffect {

    KrarkTheThumblessEffect() {
        super(Outcome.Benefit);
        staticText = "flip a coin. If you lose the flip, return that spell to its owner's hand. " +
                "If you win the flip, copy that spell, and you may choose new targets for the copy";
    }

    private KrarkTheThumblessEffect(final KrarkTheThumblessEffect effect) {
        super(effect);
    }

    @Override
    public KrarkTheThumblessEffect copy() {
        return new KrarkTheThumblessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        if (player == null || spell == null) {
            return false;
        }
        if (player.flipCoin(source, game, true)) {
            spell.createCopyOnStack(game, source, player.getId(), true);
            return true;
        }
        if (spell.isCopy()) {
            game.getStack().remove(spell, game);
            return true;
        }
        return game.getSpell(spell.getId()) != null && player.moveCards(spell, Zone.HAND, source, game);
    }
}
