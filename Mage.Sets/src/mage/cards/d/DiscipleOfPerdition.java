package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author @stwalsh4118
 */
public final class DiscipleOfPerdition extends CardImpl {

    public DiscipleOfPerdition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Disciple of Perdition dies, choose one. If you have exactly 13 life, you may choose both.
        // * You draw a card and you lose 1 life.
        Ability ability = new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1, "you"), false);
        ability.getModes().setChooseText("choose one. If you have exactly 13 life, you may choose both.");
        ability.getModes().setMoreCondition(DiscipleOfPerditionCondition.instance);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        // * Exile target opponent's graveyard. That player loses 1 life.
        ability.addMode(new Mode(new ExileGraveyardAllTargetPlayerEffect()
                .setText("Exile target opponent's graveyard"))
                .addEffect(new LoseLifeTargetEffect(1).setText("that player loses 1 life"))
                .addTarget(new TargetOpponent()));
        this.addAbility(ability);
    }

    private DiscipleOfPerdition(final DiscipleOfPerdition card) {
        super(card);
    }

    @Override
    public DiscipleOfPerdition copy() {
        return new DiscipleOfPerdition(this);
    }
}

enum DiscipleOfPerditionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() == 13;
    }
}
