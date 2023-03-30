
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;


//import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;

/**
 *
 * @author L_J
 */
public final class Leviathan extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands");
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public Leviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Leviathan enters the battlefield tapped and doesn't untap during your untap step.
        Ability abilityTapped = new EntersBattlefieldTappedAbility(
                "{this} enters the battlefield tapped and doesn't untap during your untap step.");
        abilityTapped.addEffect(new DontUntapInControllersUntapStepSourceEffect());
        this.addAbility(abilityTapped);

        // At the beginning of your upkeep, you may sacrifice two Islands. If you do, untap Leviathan.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(new UntapSourceEffect(), 
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, false))),
                TargetController.YOU,
                false));

        // Leviathan can't attack unless you sacrifice two Islands. (This cost is paid as attackers are declared.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeviathanCostToAttackBlockEffect()));

    }
        
    private Leviathan(final Leviathan card) {
        super(card);
    }

    @Override
    public Leviathan copy() {
        return new Leviathan(this);
    }
}


class LeviathanCostToAttackBlockEffect extends PayCostToAttackBlockEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands");
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    LeviathanCostToAttackBlockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK,
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, false)));
        staticText = "{this} can't attack unless you sacrifice two Islands. <i>(This cost is paid as attackers are declared.)</i>";
    }

    LeviathanCostToAttackBlockEffect(LeviathanCostToAttackBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public LeviathanCostToAttackBlockEffect copy() {
        return new LeviathanCostToAttackBlockEffect(this);
    }

}
