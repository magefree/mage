
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public final class LodestoneGolem extends CardImpl {

    public LodestoneGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Nonartifact spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LodestoneGolemCostReductionEffect()));
    }

    public LodestoneGolem(final LodestoneGolem card) {
        super(card);
    }

    @Override
    public LodestoneGolem copy() {
        return new LodestoneGolem(this);
    }
}

class LodestoneGolemCostReductionEffect extends CostModificationEffectImpl {

    LodestoneGolemCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Nonartifact spells cost {1} more to cast";
    }

    LodestoneGolemCostReductionEffect(LodestoneGolemCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        spellAbility.getManaCostsToPay().add(new GenericManaCost(1));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card card = game.getCard(abilityToModify.getSourceId());
            if (card != null && !card.isArtifact()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LodestoneGolemCostReductionEffect copy() {
        return new LodestoneGolemCostReductionEffect(this);
    }

}
