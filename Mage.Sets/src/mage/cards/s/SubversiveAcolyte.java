package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.*;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class SubversiveAcolyte extends CardImpl {

    public SubversiveAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}: Pay 2 life: Choose one. Activate only once.
        // *  Subversive Acolyte becomes a Human Cleric. It gets +1/+1 and gains lifelink.
        Ability ability = new ActivateOncePerGameActivatedAbility(Zone.BATTLEFIELD, new AddCardSubTypeSourceEffect(Duration.Custom, SubType.HUMAN, SubType.CLERIC).setText("{this} becomes a Human Cleric"),
                new GenericManaCost(2), TimingRule.INSTANT);
        ability.addEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield));
        ability.addCost(new PayLifeCost(2));

        // * Subversive Acolyte becomes a Phyrexian. It gets +3/+2 and gains trample and “Whenever this creature is dealt damage, sacrifice that many permanents.”
        ability.addMode(new Mode(new PhyrexianModeEffect()));
        this.addAbility(ability);
    }

    private SubversiveAcolyte(final SubversiveAcolyte card) {
        super(card);
    }

    @Override
    public SubversiveAcolyte copy() {
        return new SubversiveAcolyte(this);
    }
}

class PhyrexianModeEffect extends OneShotEffect {

    PhyrexianModeEffect() {
        super(Outcome.Benefit);
        staticText = "Subversive Acolyte becomes a Phyrexian. It gets +3/+2 " +
                "and gains trample and “Whenever this creature is dealt damage, sacrifice that many permanents.";
    }

    private PhyrexianModeEffect(final PhyrexianModeEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianModeEffect copy() {
        return new PhyrexianModeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null ) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(Duration.Custom, SubType.PHYREXIAN), source);
        game.addEffect(new BoostSourceEffect(3, 2, Duration.WhileOnBattlefield), source);
        game.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),source);
        game.addEffect(new GainAbilitySourceEffect(new SubversiveAcolyteTrigerredAbility(), Duration.WhileOnBattlefield),source);
        return true;
    }
}
class SubversiveAcolyteTrigerredAbility extends TriggeredAbilityImpl {
    SubversiveAcolyteTrigerredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterPermanent(), 0,""));
    }

    private SubversiveAcolyteTrigerredAbility(final SubversiveAcolyteTrigerredAbility ability) {
        super(ability);
    }

    @Override
    public mage.cards.s.SubversiveAcolyteTrigerredAbility copy() {
        return new mage.cards.s.SubversiveAcolyteTrigerredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            UUID controller = game.getControllerId(event.getTargetId());
            if (controller != null) {
                Player player = game.getPlayer(controller);
                if (player != null) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                    ((SacrificeEffect) getEffects().get(0)).setAmount(StaticValue.get(event.getAmount()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, sacrifice that many permanents.";
    }
}