
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class EmissaryOfHope extends CardImpl {

    public EmissaryOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new EmissaryOfHopeEffect(), false, true));
    }

    private EmissaryOfHope(final EmissaryOfHope card) {
        super(card);
    }

    @Override
    public EmissaryOfHope copy() {
        return new EmissaryOfHope(this);
    }
}

class EmissaryOfHopeEffect extends OneShotEffect {
    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    EmissaryOfHopeEffect() {
        super(Outcome.GainLife);
        staticText = "you gain 1 life for each artifact that player controls";
    }

    EmissaryOfHopeEffect(final EmissaryOfHopeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && sourcePlayer != null) {
            int amount = game.getBattlefield().count(filter, targetPlayer.getId(), source, game);
            if (amount > 0) {
                sourcePlayer.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public EmissaryOfHopeEffect copy() {
        return new EmissaryOfHopeEffect(this);
    }
}