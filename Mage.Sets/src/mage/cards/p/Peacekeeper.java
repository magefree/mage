package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Peacekeeper extends CardImpl {

    public Peacekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, sacrifice Peacekeeper unless you pay {1}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{1}{W}")), TargetController.YOU, false));

        // Creatures can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PeacekeeperCantAttackEffect()));
    }

    private Peacekeeper(final Peacekeeper card) {
        super(card);
    }

    @Override
    public Peacekeeper copy() {
        return new Peacekeeper(this);
    }
}

class PeacekeeperCantAttackEffect extends RestrictionEffect {

    public PeacekeeperCantAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures can't attack";
    }

    public PeacekeeperCantAttackEffect(final PeacekeeperCantAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public PeacekeeperCantAttackEffect copy() {
        return new PeacekeeperCantAttackEffect(this);
    }

}
