
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class NehebTheEternal extends CardImpl {

    public NehebTheEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Afflict 3
        addAbility(new AfflictAbility(3));

        // At the beginning of your postcombat main phase, add {R} for each 1 life your opponents have lost this turn.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(new NehebTheEternalManaEffect(), TargetController.YOU, false));
    }

    public NehebTheEternal(final NehebTheEternal card) {
        super(card);
    }

    @Override
    public NehebTheEternal copy() {
        return new NehebTheEternal(this);
    }
}

class NehebTheEternalManaEffect extends ManaEffect {

    NehebTheEternalManaEffect() {
        super();
        this.staticText = "add {R} for each 1 life your opponents have lost this turn";
    }

    NehebTheEternalManaEffect(final NehebTheEternalManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        return Mana.RedMana(new OpponentsLostLifeCount().calculate(game, source, this));
    }

    @Override
    public NehebTheEternalManaEffect copy() {
        return new NehebTheEternalManaEffect(this);
    }
}
