
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class SlayersCleaver extends CardImpl {

    public SlayersCleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+1 and must be blocked by an Eldrazi if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 1));
        ability.addEffect(new SlayersCleaverEffect());
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{4}"), false));
    }

    private SlayersCleaver(final SlayersCleaver card) {
        super(card);
    }

    @Override
    public SlayersCleaver copy() {
        return new SlayersCleaver(this);
    }
}

class SlayersCleaverEffect extends RequirementEffect {

    public SlayersCleaverEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public SlayersCleaverEffect(Duration duration) {
        super(duration);
        staticText = "and must be blocked by an Eldrazi if able";
    }

    public SlayersCleaverEffect(final SlayersCleaverEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null || equipment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedCreature = game.getPermanent(equipment.getAttachedTo());
        return attachedCreature != null && attachedCreature.isAttacking()
                && permanent.canBlock(equipment.getAttachedTo(), game) && permanent.hasSubtype(SubType.ELDRAZI, game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustBlockAttackerIfElseUnblocked(Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null) {
            return equipment.getAttachedTo();
        }
        return null;
    }

    @Override
    public SlayersCleaverEffect copy() {
        return new SlayersCleaverEffect(this);
    }

}
