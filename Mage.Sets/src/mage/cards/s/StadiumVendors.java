package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class StadiumVendors extends CardImpl {

    public StadiumVendors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Stadium Vendors enters the battlefield, choose a player. That player adds two mana of any one color they choose.
        Ability ability = new EntersBattlefieldTriggeredAbility(new StadiumVendorsEffect(), false);
        this.addAbility(ability);
    }

    private StadiumVendors(final StadiumVendors card) {
        super(card);
    }

    @Override
    public StadiumVendors copy() {
        return new StadiumVendors(this);
    }
}

class StadiumVendorsEffect extends OneShotEffect {

    StadiumVendorsEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a player. That player adds two mana of any one color they choose";
    }

    StadiumVendorsEffect(final StadiumVendorsEffect effect) {
        super(effect);
    }

    @Override
    public StadiumVendorsEffect copy() {
        return new StadiumVendorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPlayer target = new TargetPlayer(1, 1, true);
        if (controller.choose(Outcome.Benefit, target, source, game)) {
            Player player = game.getPlayer(target.getFirstTarget());
            ChoiceColor colorChoice = new ChoiceColor(true);
            if (player == null
                    || !player.choose(Outcome.Benefit, colorChoice, game)) {
                return false;
            }
            Effect effect = new AddManaToManaPoolTargetControllerEffect(colorChoice.getMana(2), "that player's");
            effect.setTargetPointer(new FixedTarget(player.getId(), game));
            return effect.apply(game, source);
        }
        return false;
    }
}
