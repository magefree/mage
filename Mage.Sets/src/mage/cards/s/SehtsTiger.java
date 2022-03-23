
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SehtsTiger extends CardImpl {

    public SehtsTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Seht's Tiger enters the battlefield, you gain protection from the color of your choice until end of turn.
        // (You can't be targeted, dealt damage, or enchanted by anything of the chosen color.)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SehtsTigerEffect(), false));

    }

    private SehtsTiger(final SehtsTiger card) {
        super(card);
    }

    @Override
    public SehtsTiger copy() {
        return new SehtsTiger(this);
    }
}

class SehtsTigerEffect extends OneShotEffect {

    public SehtsTigerEffect() {
        super(Outcome.Protect);
        staticText = "you gain protection from the color of your choice until end of turn <i>(You can't be targeted, dealt damage, or enchanted by anything of the chosen color.)</i>";
    }

    public SehtsTigerEffect(final SehtsTigerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && mageObject != null && controller.choose(Outcome.Protect, choice, game)) {
            game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
            FilterCard filter = new FilterCard();
            filter.add(new ColorPredicate(choice.getColor()));
            filter.setMessage(choice.getChoice());
            Ability ability = new ProtectionAbility(filter);
            game.addEffect(new GainAbilityControllerEffect(ability, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }

    @Override
    public SehtsTigerEffect copy() {
        return new SehtsTigerEffect(this);
    }

}
