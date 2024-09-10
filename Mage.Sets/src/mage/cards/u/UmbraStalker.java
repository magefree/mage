package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class UmbraStalker extends CardImpl {

    public UmbraStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Chroma - Umbra Stalker's power and toughness are each equal to the number of black mana symbols in the mana costs of cards in your graveyard.
        DynamicValue xValue = new ChromaUmbraStalkerCount();
        Effect effect = new SetBasePowerToughnessSourceEffect(xValue);
        effect.setText("<i>Chroma</i> &mdash; Umbra Stalker's power and toughness are each equal to the number of black mana symbols in the mana costs of cards in your graveyard.");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect)
                .addHint(new ValueHint("Black mana symbols in your graveyard's permanents", xValue))
        );
    }

    private UmbraStalker(final UmbraStalker card) {
        super(card);
    }

    @Override
    public UmbraStalker copy() {
        return new UmbraStalker(this);
    }
}

class ChromaUmbraStalkerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int chroma = 0;
        Player you = game.getPlayer(sourceAbility.getControllerId());
        if (you == null) {
            return 0;
        }
        for (Card card : you.getGraveyard().getCards(game)) {
            chroma += card.getManaCost().getMana().getBlack();
        }
        return chroma;
    }

    @Override
    public ChromaUmbraStalkerCount copy() {
        return new ChromaUmbraStalkerCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
