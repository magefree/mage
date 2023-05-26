package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KlothysGodOfDestiny extends CardImpl {

    public KlothysGodOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to red and green is less than seven, Klothys isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.RG, 7))
                .addHint(DevotionCount.RG.getHint()));

        // At the beginning of your precombat main phase, exile target card from a graveyard. If it was a land card, add {R} or {G}. Otherwise, you gain 2 life and Klothys deals 2 damage to each opponent.
        Ability ability = new BeginningOfPreCombatMainTriggeredAbility(
                new KlothysGodOfDestinyEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private KlothysGodOfDestiny(final KlothysGodOfDestiny card) {
        super(card);
    }

    @Override
    public KlothysGodOfDestiny copy() {
        return new KlothysGodOfDestiny(this);
    }
}

class KlothysGodOfDestinyEffect extends OneShotEffect {

    KlothysGodOfDestinyEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card from a graveyard. If it was a land card, add {R} or {G}. " +
                "Otherwise, you gain 2 life and {this} deals 2 damage to each opponent.";
    }

    private KlothysGodOfDestinyEffect(final KlothysGodOfDestinyEffect effect) {
        super(effect);
    }

    @Override
    public KlothysGodOfDestinyEffect copy() {
        return new KlothysGodOfDestinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        boolean isLand = card.isLand(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (isLand) {
            Mana mana = new Mana();
            if (player.chooseUse(
                    Outcome.PutManaInPool, "Choose a color of mana to add",
                    null, "Red", "Green", source, game
            )) {
                mana.increaseRed();
            } else {
                mana.increaseGreen();
            }
            player.getManaPool().addMana(mana, game, source);
            return true;
        }
        player.gainLife(2, game, source);
        game.getOpponents(player.getId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEach(opponent -> opponent.damage(2, source.getSourceId(), source, game));
        return true;
    }
}
