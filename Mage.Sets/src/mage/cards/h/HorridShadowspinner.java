package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HorridShadowspinner extends CardImpl {

    public HorridShadowspinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Horrid Shadowspinner attacks, you may draw cards equal to its power. If you do, discard that many cards.
        this.addAbility(new AttacksTriggeredAbility(new HorridShadowspinnerEffect(), true));
    }

    private HorridShadowspinner(final HorridShadowspinner card) {
        super(card);
    }

    @Override
    public HorridShadowspinner copy() {
        return new HorridShadowspinner(this);
    }
}

class HorridShadowspinnerEffect extends OneShotEffect {

    HorridShadowspinnerEffect() {
        super(Outcome.Benefit);
        staticText = "draw cards equal to its power. If you do, discard that many cards";
    }

    private HorridShadowspinnerEffect(final HorridShadowspinnerEffect effect) {
        super(effect);
    }

    @Override
    public HorridShadowspinnerEffect copy() {
        return new HorridShadowspinnerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        int power = Math.max(permanent.getPower().getValue(), 0);
        player.drawCards(power, source, game);
        player.discard(power, false, false, source, game);
        return true;
    }
}
