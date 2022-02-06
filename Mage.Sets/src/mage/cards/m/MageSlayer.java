
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MageSlayer extends CardImpl {

    public MageSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}{G}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, it deals damage equal to its power to defending player.
        this.addAbility(new AttacksAttachedTriggeredAbility(new MageSlayerEffect(), false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private MageSlayer(final MageSlayer card) {
        super(card);
    }

    @Override
    public MageSlayer copy() {
        return new MageSlayer(this);
    }
}

class MageSlayerEffect extends OneShotEffect {

    public MageSlayerEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to the player or planeswalker it's attacking";
    }

    public MageSlayerEffect(final MageSlayerEffect effect) {
        super(effect);
    }

    @Override
    public MageSlayerEffect copy() {
        return new MageSlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            int power = game.getPermanent(equipment.getAttachedTo()).getPower().getValue();
            UUID defenderId = game.getCombat().getDefenderId(equipment.getAttachedTo());
            if (power > 0 && defenderId != null) {
                UUID sourceId = (UUID) this.getValue("sourceId");
                if (sourceId != null) {
                    game.damagePlayerOrPlaneswalker(defenderId, power, source.getSourceId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
