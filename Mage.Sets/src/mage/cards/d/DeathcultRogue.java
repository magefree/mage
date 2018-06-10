
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class DeathcultRogue extends CardImpl {

    public DeathcultRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U/B}{U/B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathcult Rogue can't be blocked except by Rogues.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DeathcultRogueRestrictionEffect()));

    }

    public DeathcultRogue(final DeathcultRogue card) {
        super(card);
    }

    @Override
    public DeathcultRogue copy() {
        return new DeathcultRogue(this);
    }
}

class DeathcultRogueRestrictionEffect extends RestrictionEffect  {

    public DeathcultRogueRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Deathcult Rogue can't be blocked except by Rogues";
    }

    public DeathcultRogueRestrictionEffect(final DeathcultRogueRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.hasSubtype(SubType.ROGUE, game)) {
            return true;
        }
        return false;
    }

    @Override
    public DeathcultRogueRestrictionEffect copy() {
        return new DeathcultRogueRestrictionEffect(this);
    }
}
