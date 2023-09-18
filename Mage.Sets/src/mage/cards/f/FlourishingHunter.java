package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class FlourishingHunter extends CardImpl {

    public FlourishingHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Flourishing Hunter enters the battlefield, you gain life equal to the greatest toughness among other creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FlourishingHunterEffect()));
    }

    private FlourishingHunter(final FlourishingHunter card) {
        super(card);
    }

    @Override
    public FlourishingHunter copy() {
        return new FlourishingHunter(this);
    }
}

class FlourishingHunterEffect extends OneShotEffect {

    public FlourishingHunterEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to the greatest toughness among other creatures you control";
    }

    private FlourishingHunterEffect(final FlourishingHunterEffect effect) {
        super(effect);
    }

    @Override
    public FlourishingHunterEffect copy() {
        return new FlourishingHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int greatestToughness = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
            if (permanent.isCreature(game) && !permanent.getId().equals(source.getSourceId())) {
                int toughness = permanent.getToughness().getValue();
                if (toughness > greatestToughness) {
                    greatestToughness = toughness;
                }
            }
        }
        controller.gainLife(greatestToughness, game, source);
        return true;
    }
}
