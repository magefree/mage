package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public final class AvoidedStairwurm extends CardImpl {

    public AvoidedStairwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(new MenaceAbility());

        // This creature gets -1/-1 for each creature card in your library.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                AvoidedStairwurmValue.instance, AvoidedStairwurmValue.instance, Duration.WhileOnBattlefield
        ).setText("{this} gets -1/-1 for each creature card in your library")));
    }

    private AvoidedStairwurm(final AvoidedStairwurm card) {
        super(card);
    }

    @Override
    public AvoidedStairwurm copy() {
        return new AvoidedStairwurm(this);
    }
}

enum AvoidedStairwurmValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Player player = game.getPlayer(source.getControllerId());
        return player == null ? 0 : -(int) player.getLibrary().getCards(game).stream()
                .filter(card -> card.isCreature(game)).count();
    }

    @Override
    public AvoidedStairwurmValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature card in your library";
    }

    @Override
    public int getSign() {
        return -1;
    }
}
