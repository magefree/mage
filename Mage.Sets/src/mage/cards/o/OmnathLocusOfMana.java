
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManaTypeInManaPoolCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class OmnathLocusOfMana extends CardImpl {

    public OmnathLocusOfMana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Green mana doesn't empty from your mana pool as steps and phases end.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OmnathRuleEffect()));

        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool
        DynamicValue boost = new ManaTypeInManaPoolCount(ManaType.GREEN);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(boost, boost, Duration.WhileOnBattlefield)));

    }

    private OmnathLocusOfMana(final OmnathLocusOfMana card) {
        super(card);
    }

    @Override
    public OmnathLocusOfMana copy() {
        return new OmnathLocusOfMana(this);
    }
}

class OmnathRuleEffect extends ContinuousEffectImpl {

    public OmnathRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You don't lose unspent green mana as steps and phases end";
    }

    public OmnathRuleEffect(final OmnathRuleEffect effect) {
        super(effect);
    }

    @Override
    public OmnathRuleEffect copy() {
        return new OmnathRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addDoNotEmptyManaType(ManaType.GREEN);
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
