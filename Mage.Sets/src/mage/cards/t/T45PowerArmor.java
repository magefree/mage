package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 *
 * @author justinjohnson14
 */
public final class T45PowerArmor extends CardImpl {

    public T45PowerArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // When T-45 Power Armor enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2), false));
        // Equipped creature gets +3/+3 and doesn't untap during its controller's untap step.
        Ability ability = (new SimpleStaticAbility(new BoostEquippedEffect(3,3)));
        ability.addEffect(new DontUntapInControllersUntapStepEnchantedEffect().setText("and doesn't untap during its controller's untap step"));
        this.addAbility(ability);

        // At the beginning of your upkeep, you may pay {E}. If you do, untap equipped creature, then put your choice of a menace, trample or lifelink counter on it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new T45PowerArmorEffect(),
                        new PayEnergyCost(1)
                ),
                TargetController.YOU,
                false
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private T45PowerArmor(final T45PowerArmor card) {
        super(card);
    }

    @Override
    public T45PowerArmor copy() {
        return new T45PowerArmor(this);
    }
}

class T45PowerArmorEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>(Arrays.asList("Menace", "Trample", "Lifelink"));

    T45PowerArmorEffect() {
        super(Outcome.BoostCreature);
        staticText = "untap equipped creature, then put your choice of a menace, trample, or lifelink counter on it";
    }

    private T45PowerArmorEffect(T45PowerArmorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent attachment = source.getSourcePermanentIfItStillExists(game);
        if (player == null || attachment == null) {
            return false;
        }
        Permanent creature = game.getPermanent(attachment.getAttachedTo());
        if (creature == null) {
            return false;
        }

        creature.untap(game);

        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose menace, trample, or lifelink");
        choice.setChoices(choices);
        player.choose(outcome, choice, game);
        String chosen = choice.getChoice();
        if (chosen != null) {
            creature.addCounters(CounterType.findByName(
                    chosen.toLowerCase(Locale.ENGLISH)
            ).createInstance(), source.getControllerId(), source, game);
        }

        return true;
    }

    @Override
    public T45PowerArmorEffect copy() {
        return new T45PowerArmorEffect(this);
    }
}
