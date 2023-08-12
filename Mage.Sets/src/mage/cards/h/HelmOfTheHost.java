
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Will
 */
public final class HelmOfTheHost extends CardImpl {

    public HelmOfTheHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // At the beginning of combat on your turn, create a token that's a copy of equipped creature, except the token isn't legendary if equipped creature is legendary. That token gains haste.
        TriggeredAbility ability = new BeginningOfCombatTriggeredAbility(
                new HelmOfTheHostEffect(),
                TargetController.YOU,
                false
        );
        this.addAbility(ability);

        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(5), false));
    }

    private HelmOfTheHost(final HelmOfTheHost card) {
        super(card);
    }

    @Override
    public HelmOfTheHost copy() {
        return new HelmOfTheHost(this);
    }
}

class HelmOfTheHostEffect extends OneShotEffect {

    public HelmOfTheHostEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of equipped creature, except the token isn't legendary if equipped creature is legendary. That token gains haste.";
    }

    public HelmOfTheHostEffect(final HelmOfTheHostEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfTheHostEffect copy() {
        return new HelmOfTheHostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (equipment == null) {
            return true;
        }
        Permanent creature = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
        if (creature == null) {
            return true;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
        effect.setTargetPointer(new FixedTarget(creature, game));
        effect.setIsntLegendary(true);
        return effect.apply(game, source);
    }

}
