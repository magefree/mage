
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SelvalaExplorerReturned extends CardImpl {

    public SelvalaExplorerReturned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Parley - {T}: Each player reveals the top card of their library. For each nonland card revealed this way, add {G} and you gain 1 life. Then each player draws a card.
        ActivatedManaAbilityImpl manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, new SelvalaExplorerReturnedEffect(), new TapSourceCost(), false);
        manaAbility.setUndoPossible(false);
        manaAbility.setAbilityWord(AbilityWord.PARLEY);
        Effect effect = new DrawCardAllEffect(1);
        effect.setText("Then each player draws a card");
        manaAbility.addEffect(effect);
        this.addAbility(manaAbility);
    }

    public SelvalaExplorerReturned(final SelvalaExplorerReturned card) {
        super(card);
    }

    @Override
    public SelvalaExplorerReturned copy() {
        return new SelvalaExplorerReturned(this);
    }
}

class SelvalaExplorerReturnedEffect extends ManaEffect {

    public SelvalaExplorerReturnedEffect() {
        this.staticText = "Each player reveals the top card of their library. For each nonland card revealed this way, add {G} and you gain 1 life";
    }

    public SelvalaExplorerReturnedEffect(final SelvalaExplorerReturnedEffect effect) {
        super(effect);
    }

    @Override
    public SelvalaExplorerReturnedEffect copy() {
        return new SelvalaExplorerReturnedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Mana mana = getMana(game, source);
            if (mana.getGreen() > 0) {
                controller.getManaPool().addMana(mana, game, source);
                controller.gainLife(mana.getGreen(), game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        if (netMana) {

        }
        return Mana.GreenMana(ParleyCount.getInstance().calculate(game, source, this));
    }

}
