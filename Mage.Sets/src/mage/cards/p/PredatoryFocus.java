package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author noahg
 */
public final class PredatoryFocus extends CardImpl {

    public PredatoryFocus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // You may have creatures you control assign their combat damage this turn as though they weren't blocked.
        this.getSpellAbility().addEffect(new PredatoryFocusEffect());
    }

    private PredatoryFocus(final PredatoryFocus card) {
        super(card);
    }

    @Override
    public PredatoryFocus copy() {
        return new PredatoryFocus(this);
    }
}

class PredatoryFocusEffect extends AsThoughEffectImpl {

    private boolean choseUse;

    public PredatoryFocusEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.EndOfTurn, Outcome.Damage);
        this.staticText = "You may have creatures you control assign their combat damage this turn as though they weren't blocked.";
    }

    public PredatoryFocusEffect(PredatoryFocusEffect effect) {
        super(effect);
        this.choseUse = effect.choseUse;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        String sourceName = source.getSourceObject(game).getLogName();
        choseUse = controller.chooseUse(Outcome.Damage, "Have creatures you control deal combat damage this turn"
                + " as though they weren't blocked?", source, game);
        game.informPlayers(choseUse ? controller.getName() + " chose to use " + sourceName + "'s effect"
                : controller.getName() + " chose not to use " + sourceName + "'s effect.");
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return choseUse && affectedControllerId.equals(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PredatoryFocusEffect copy() {
        return new PredatoryFocusEffect(this);
    }
}
