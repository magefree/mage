
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class SavageVentmaw extends CardImpl {

    public SavageVentmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Savage Ventmaw attacks, add {R}{R}{R}{G}{G}{G}. Until end of turn, you don't lose this mana as steps and phases end.
        Effect effect = new SavageVentmawManaEffect();
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    public SavageVentmaw(final SavageVentmaw card) {
        super(card);
    }

    @Override
    public SavageVentmaw copy() {
        return new SavageVentmaw(this);
    }
}

class SavageVentmawManaEffect extends ManaEffect {

    protected Mana mana;

    public SavageVentmawManaEffect() {
        super();
        this.mana = new Mana(3, 3, 0, 0, 0, 0, 0, 0);
        this.staticText = "add " + mana.toString() + ". Until end of turn, you don't lose this mana as steps and phases end";
    }

    public SavageVentmawManaEffect(final SavageVentmawManaEffect effect) {
        super(effect);
        this.mana = effect.mana;
        this.staticText = effect.staticText;
    }

    @Override
    public SavageVentmawManaEffect copy() {
        return new SavageVentmawManaEffect(this);
    }

    @Override
    protected void addManaToPool(Player player, Mana manaToAdd, Game game, Ability source) {
        player.getManaPool().addMana(manaToAdd, game, source, true);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return mana.copy();
    }

}
