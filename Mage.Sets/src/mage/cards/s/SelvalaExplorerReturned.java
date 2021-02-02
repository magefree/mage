package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
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

    private SelvalaExplorerReturned(final SelvalaExplorerReturned card) {
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
    public List<Mana> getNetMana(Game game, Ability source) {
        return new ArrayList<>();
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            int parleyCount = ParleyCount.getInstance().calculate(game, source, this);
            Player player = getPlayer(game, source);
            if (player != null) {
                player.gainLife(parleyCount, game, source);
            }
            return Mana.GreenMana(parleyCount);
        }
        return new Mana();
    }
}
