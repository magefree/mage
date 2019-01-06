package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Gutterbones extends CardImpl {

    public Gutterbones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Gutterbones enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {1}{B}: Return Gutterbones from your graveyard to your hand. Activate this ability only during your turn and only if an opponent lost life this turn.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl("{1}{B}"), GutterbonesCondition.instance
        ));
    }

    private Gutterbones(final Gutterbones card) {
        super(card);
    }

    @Override
    public Gutterbones copy() {
        return new Gutterbones(this);
    }
}

enum GutterbonesCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.isActivePlayer(source.getControllerId())) {
            PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
            return watcher != null && watcher.getAllOppLifeLost(source.getControllerId(), game) > 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "during your turn and only if an opponent lost life this turn";
    }
}