
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HellkiteCharger extends CardImpl {

    public HellkiteCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());


        // Whenever Hellkite Charger attacks, you may pay {5}{R}{R}. If you do, untap all attacking creatures and after this phase, there is an additional combat phase.
        this.addAbility(new AttacksTriggeredAbility(new HellkiteChargerEffect(),false));
    }

    private HellkiteCharger(final HellkiteCharger card) {
        super(card);
    }

    @Override
    public HellkiteCharger copy() {
        return new HellkiteCharger(this);
    }
}

class HellkiteChargerEffect extends OneShotEffect {

    HellkiteChargerEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {5}{R}{R}. If you do, untap all attacking creatures and after this phase, there is an additional combat phase";
    }

    HellkiteChargerEffect(final HellkiteChargerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            ManaCosts cost = new ManaCostsImpl<>("{5}{R}{R}");
            if (player.chooseUse(Outcome.Damage, "Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    new UntapAllControllerEffect(new FilterAttackingCreature(),"").apply(game, source);
                    game.getState().getTurnMods().add(new TurnMod(source.getControllerId()).withExtraPhase(TurnPhase.COMBAT));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public HellkiteChargerEffect copy() {
        return new HellkiteChargerEffect(this);
    }

}
