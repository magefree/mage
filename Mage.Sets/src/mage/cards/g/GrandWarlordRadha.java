package mage.cards.g;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class GrandWarlordRadha extends CardImpl {

    public GrandWarlordRadha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more creatures you control attack, add that much mana in any combination of {R} and/or {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new GrandWarlordRadhaEffect(), 1
        ).setTriggerPhrase("Whenever one or more creatures you control attack, "));
    }

    private GrandWarlordRadha(final GrandWarlordRadha card) {
        super(card);
    }

    @Override
    public GrandWarlordRadha copy() {
        return new GrandWarlordRadha(this);
    }
}

class GrandWarlordRadhaEffect extends OneShotEffect {

    GrandWarlordRadhaEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "add that much mana in any combination of {R} and/or {G}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private GrandWarlordRadhaEffect(final GrandWarlordRadhaEffect effect) {
        super(effect);
    }

    @Override
    public GrandWarlordRadhaEffect copy() {
        return new GrandWarlordRadhaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int amount = (Integer) getValue(AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_ATTACKERS);
        if (controller == null || amount < 1) {
            return false;
        }
        List<Integer> manaList = controller.getMultiAmount(this.outcome, Arrays.asList("R", "G"), 0, amount, amount, MultiAmountType.MANA, game);

        Mana mana = new Mana();
        mana.add(new Mana(ColoredManaSymbol.R, manaList.get(0)));
        mana.add(new Mana(ColoredManaSymbol.G, manaList.get(1)));

        controller.getManaPool().addMana(mana, game, source, true);
        return true;
    }
}
