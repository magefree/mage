package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author awjackson
 */
public final class MageSlayer extends CardImpl {

    public MageSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}{G}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, it deals damage equal to its power to the player or planeswalker it's attacking
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new MageSlayerEffect(), AttachmentType.EQUIPMENT, false, SetTargetPointer.PERMANENT
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
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
        staticText = "it deals damage equal to its power to the player or planeswalker it's attacking";
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
        Permanent attacker = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (attacker == null) {
            return false;
        }
        game.damagePlayerOrPlaneswalker(
                game.getCombat().getDefenderId(attacker.getId()),
                attacker.getPower().getValue(),
                attacker.getId(),
                source,
                game,
                false,
                true
        );
        return true;
    }
}
