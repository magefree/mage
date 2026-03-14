package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.*;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPool;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public final class OzaiThePhoenixKing extends CardImpl {

    public OzaiThePhoenixKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{R}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Firebending 4
        this.addAbility(new FirebendingAbility(4));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If you would lose unspent mana, that mana becomes red instead.
        this.addAbility(new SimpleStaticAbility(new OzaiThePhoenixKingManaEffect()));
        
        // Ozai has flying and indestructible as long as you have six or more unspent mana.
        this.addAbility(new SimpleStaticAbility(new OzaiThePhoenixKingBoostEffect()));
    }

    private OzaiThePhoenixKing(final OzaiThePhoenixKing card) {
        super(card);
    }

    @Override
    public OzaiThePhoenixKing copy() {
        return new OzaiThePhoenixKing(this);
    }
}

class OzaiThePhoenixKingManaEffect extends ContinuousEffectImpl {

    OzaiThePhoenixKingManaEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "if you would lose unspent mana, that mana becomes red instead";
    }

    private OzaiThePhoenixKingManaEffect(final OzaiThePhoenixKingManaEffect effect) {
        super(effect);
    }

    @Override
    public OzaiThePhoenixKingManaEffect copy() {
        return new OzaiThePhoenixKingManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().setManaBecomesRed(true);
        }
        return true;
    }
}

class OzaiThePhoenixKingBoostEffect extends ContinuousEffectImpl {

    OzaiThePhoenixKingBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has flying and indestructible as long as you have six or more unspent mana";
    }

    private OzaiThePhoenixKingBoostEffect(final OzaiThePhoenixKingBoostEffect effect) {
        super(effect);
    }

    @Override
    public OzaiThePhoenixKingBoostEffect copy() {
        return new OzaiThePhoenixKingBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(source.getSourceId());
        if (controller == null || creature == null) {
            return false;
        }
        ManaPool pool = controller.getManaPool();
        int blackMana = pool.getBlack();
        int whiteMana = pool.getWhite();
        int blueMana = pool.getBlue();
        int greenMana = pool.getGreen();
        int redMana = pool.getRed();
        int colorlessMana = pool.getColorless();
        int manaPoolTotal = blackMana + whiteMana + blueMana + greenMana + redMana + colorlessMana;
        if (manaPoolTotal >= 6) {
            creature.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
            creature.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
            return true;
        }
        return false;
    }

}