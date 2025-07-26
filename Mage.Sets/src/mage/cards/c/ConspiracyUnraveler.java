package mage.cards.c;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class ConspiracyUnraveler extends CardImpl {

    public ConspiracyUnraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may collect evidence 10 rather than pay the mana cost for spells that you cast.
        this.addAbility(new SimpleStaticAbility(new ConspiracyUnravelerInsteadEffect()));
    }

    private ConspiracyUnraveler(final ConspiracyUnraveler card) {
        super(card);
    }

    @Override
    public ConspiracyUnraveler copy() {
        return new ConspiracyUnraveler(this);
    }
}

// Inspired by WUBRGInsteadEffect
class ConspiracyUnravelerInsteadEffect extends ContinuousEffectImpl {

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(new CollectEvidenceCost(10), SourceIsSpellCondition.instance);

    ConspiracyUnravelerInsteadEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "You may collect evidence 10 rather than pay the mana cost for spells that you cast";
    }

    protected ConspiracyUnravelerInsteadEffect(final ConspiracyUnravelerInsteadEffect effect) {
        super(effect);
    }

    @Override
    public ConspiracyUnravelerInsteadEffect copy() {
        return new ConspiracyUnravelerInsteadEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((Player) object).getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            affectedObjects.add(controller);
            return true;
        }
        return false;
    }

}
