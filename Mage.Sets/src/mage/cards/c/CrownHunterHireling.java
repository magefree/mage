package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CrownHunterHireling extends CardImpl {

    public CrownHunterHireling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Crown-Hunter Hireling enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // Crown-Hunter Hireling can't attack unless defending player is the monarch.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CrownHunterHirelingCantAttackEffect()));
    }

    private CrownHunterHireling(final CrownHunterHireling card) {
        super(card);
    }

    @Override
    public CrownHunterHireling copy() {
        return new CrownHunterHireling(this);
    }
}

class CrownHunterHirelingCantAttackEffect extends RestrictionEffect {

    public CrownHunterHirelingCantAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless defending player is the monarch";
    }

    private CrownHunterHirelingCantAttackEffect(final CrownHunterHirelingCantAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        return defenderId.equals(game.getMonarchId());
    }

    @Override
    public CrownHunterHirelingCantAttackEffect copy() {
        return new CrownHunterHirelingCantAttackEffect(this);
    }

}
