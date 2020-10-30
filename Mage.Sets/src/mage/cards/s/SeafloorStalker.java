package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeafloorStalker extends CardImpl {

    public SeafloorStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {4}{U}: Seafloor Stalker gets +1/+0 until end of turn and can't be blocked this turn. This ability costs {1} less to activate for each creature in your party.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{U}")
        );
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("and can't be blocked this turn"));
        ability.addEffect(new InfoEffect(
                "This ability costs {1} less to activate for each creature in your party. " + PartyCount.getReminder()
        ));
        ability.setCostAdjuster(SeafloorStalkerAdjuster.instance);
        this.addAbility(ability.addHint(PartyCountHint.instance));
    }

    private SeafloorStalker(final SeafloorStalker card) {
        super(card);
    }

    @Override
    public SeafloorStalker copy() {
        return new SeafloorStalker(this);
    }
}

enum SeafloorStalkerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            int count = PartyCount.instance.calculate(game, ability, null);
            CardUtil.reduceCost(ability, count);
        }
    }
}
