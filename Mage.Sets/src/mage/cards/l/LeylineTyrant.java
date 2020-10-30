package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;
import mage.abilities.common.DiesSourceTriggeredAbility;

/**
 * @author TheElk801
 */
public final class LeylineTyrant extends CardImpl {

    public LeylineTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You don't lose unspent red mana as steps and phases end.
        this.addAbility(new SimpleStaticAbility(new LeylineTyrantManaEffect()));

        // When Leyline Tyrant dies, you may pay any amount of {R}. When you do, it deals that much damage to any target.
        this.addAbility(new DiesSourceTriggeredAbility(new LeylineTyrantDamageEffect()));
    }

    private LeylineTyrant(final LeylineTyrant card) {
        super(card);
    }

    @Override
    public LeylineTyrant copy() {
        return new LeylineTyrant(this);
    }
}

class LeylineTyrantManaEffect extends ContinuousEffectImpl {

    LeylineTyrantManaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You don't lose unspent red mana as steps and phases end";
    }

    private LeylineTyrantManaEffect(final LeylineTyrantManaEffect effect) {
        super(effect);
    }

    @Override
    public LeylineTyrantManaEffect copy() {
        return new LeylineTyrantManaEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addDoNotEmptyManaType(ManaType.RED);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

class LeylineTyrantDamageEffect extends OneShotEffect {

    LeylineTyrantDamageEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay any amount of {R}. When you do, it deals that much damage to any target";
    }

    private LeylineTyrantDamageEffect(final LeylineTyrantDamageEffect effect) {
        super(effect);
    }

    @Override
    public LeylineTyrantDamageEffect copy() {
        return new LeylineTyrantDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int costX = player.announceXMana(
                0, Integer.MAX_VALUE,
                "Announce the value for {X}", game, source
        );
        String manaString;
        if (costX == 0) {
            manaString = "{0}";
        } else {
            manaString = "";
            for (int i = 0; i < costX; i++) {
                manaString += "{R}";
            }
        }
        Cost cost = new ManaCostsImpl<>(manaString);
        cost.clearPaid();
        if (!cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(costX), false,
                "{this} deals " + costX + " damage to any target"
        );
        ability.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
