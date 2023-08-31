
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author magenoxx_at_gmail.com
 */
public final class WarmongersChariot extends CardImpl {

    public WarmongersChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));

        // As long as equipped creature has defender, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WarmongersChariotEffect()));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private WarmongersChariot(final WarmongersChariot card) {
        super(card);
    }

    @Override
    public WarmongersChariot copy() {
        return new WarmongersChariot(this);
    }
}

class WarmongersChariotEffect extends AsThoughEffectImpl {

    public WarmongersChariotEffect() {
        super(AsThoughEffectType.ATTACK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as equipped creature has defender, it can attack as though it didn't have defender";
    }

    private WarmongersChariotEffect(final WarmongersChariotEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WarmongersChariotEffect copy() {
        return new WarmongersChariotEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null && creature.getId().equals(sourceId)
                    && creature.getAbilities().containsKey(DefenderAbility.getInstance().getId())) {
                return true;
            }
        }
        return false;
    }

}
