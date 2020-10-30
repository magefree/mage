package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclavePickAxe extends CardImpl {

    public SkyclavePickAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Skyclave Pick-Axe enters the battlefield, attach it to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Landfall — Whenever a land enters the battlefield under your control, equipped creature gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new SkyclavePickAxeEffect()));

        // Equip {2}{G}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{G}")));
    }

    private SkyclavePickAxe(final SkyclavePickAxe card) {
        super(card);
    }

    @Override
    public SkyclavePickAxe copy() {
        return new SkyclavePickAxe(this);
    }
}

class SkyclavePickAxeEffect extends OneShotEffect {

    SkyclavePickAxeEffect() {
        super(Outcome.Benefit);
        staticText = "equipped creature gets +2/+2 until end of turn";
    }

    private SkyclavePickAxeEffect(final SkyclavePickAxeEffect effect) {
        super(effect);
    }

    @Override
    public SkyclavePickAxeEffect copy() {
        return new SkyclavePickAxeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null || game.getPermanent(permanent.getAttachedTo()) == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(2, 2).setTargetPointer(
                new FixedTarget(permanent.getAttachedTo(), game)
        ), source);
        return true;
    }
}
