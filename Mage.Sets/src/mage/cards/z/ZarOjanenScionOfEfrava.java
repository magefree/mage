package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.DomainHint;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class ZarOjanenScionOfEfrava extends CardImpl {

    public ZarOjanenScionOfEfrava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Domain -- Whenever Zar Ojanen, Scion of Efrava becomes tapped, put a +1/+1 counter on each creature you control with toughness less than the number of basic land types among lands you control.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new ZarOjanenScionOfEfravaEffect()).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private ZarOjanenScionOfEfrava(final ZarOjanenScionOfEfrava card) {
        super(card);
    }

    @Override
    public ZarOjanenScionOfEfrava copy() {
        return new ZarOjanenScionOfEfrava(this);
    }
}

class ZarOjanenScionOfEfravaEffect extends OneShotEffect {

    public ZarOjanenScionOfEfravaEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on each creature you control with toughness less than the number of basic land types among lands you control";
    }

    private ZarOjanenScionOfEfravaEffect(final ZarOjanenScionOfEfravaEffect effect) {
        super(effect);
    }

    @Override
    public ZarOjanenScionOfEfravaEffect copy() {
        return new ZarOjanenScionOfEfravaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        StringBuilder sb = new StringBuilder();
        if (sourceObject != null) {
            sb.append(sourceObject.getLogName());
            sb.append(": ");
        }
        if (controller != null) {
            sb.append(controller.getLogName());
        } else {
            sb.append("controller");
        }
        sb.append(" puts a +1/+1 counter on ");
        String prefix = sb.toString();
        int basicLandTypes = DomainValue.REGULAR.calculate(game, source, this);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.isCreature(game) && permanent.getToughness().getValue() < basicLandTypes) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
                if (!game.isSimulation()) {
                    game.informPlayers(prefix + permanent.getLogName());
                }
            }
        }
        return true;
    }
}
