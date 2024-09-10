package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KruphixGodOfHorizons extends CardImpl {

    public KruphixGodOfHorizons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to green and blue is less than seven, Kruhpix isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.GU, 7))
                .addHint(DevotionCount.GU.getHint()));

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // If unused mana would empty from your mana pool, that mana becomes colorless instead.
        this.addAbility(new SimpleStaticAbility(new KruphixGodOfHorizonsEffect()));
    }

    private KruphixGodOfHorizons(final KruphixGodOfHorizons card) {
        super(card);
    }

    @Override
    public KruphixGodOfHorizons copy() {
        return new KruphixGodOfHorizons(this);
    }
}

class KruphixGodOfHorizonsEffect extends ContinuousEffectImpl {

    KruphixGodOfHorizonsEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "if you would lose unspent mana, that mana becomes colorless instead";
    }

    private KruphixGodOfHorizonsEffect(final KruphixGodOfHorizonsEffect effect) {
        super(effect);
    }

    @Override
    public KruphixGodOfHorizonsEffect copy() {
        return new KruphixGodOfHorizonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().setManaBecomesColorless(true);
        }
        return true;
    }
}
