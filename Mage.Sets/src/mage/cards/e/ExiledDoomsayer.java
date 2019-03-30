
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorphCondition;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author Ketsuban
 */
public final class ExiledDoomsayer extends CardImpl {

    public ExiledDoomsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // All morph costs cost {2} more.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExiledDoomsayerSpellsTaxEffect()));
    }

    public ExiledDoomsayer(final ExiledDoomsayer card) {
        super(card);
    }

    @Override
    public ExiledDoomsayer copy() {
        return new ExiledDoomsayer(this);
    }
}

class ExiledDoomsayerSpellsTaxEffect extends CostModificationEffectImpl {

    public ExiledDoomsayerSpellsTaxEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "All morph costs cost {2} more.";
    }

    protected ExiledDoomsayerSpellsTaxEffect(final ExiledDoomsayerSpellsTaxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {

        Card card = game.getCard(abilityToModify.getSourceId());
        if (card != null) {
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof MorphAbility) {
                    if (ability.isActivated()) {
                        int amountToIncrease = ((MorphAbility) ability).increaseCost(2);
                        CardUtil.increaseCost(abilityToModify, amountToIncrease);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
                if (spell != null) {
                    if (MorphCondition.instance.apply(game, abilityToModify)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ExiledDoomsayerSpellsTaxEffect copy() {
        return new ExiledDoomsayerSpellsTaxEffect(this);
    }
}
