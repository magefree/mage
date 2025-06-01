package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.EarlyTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author Grath
 */
public final class CaptainAmericaFirstAvenger extends CardImpl {

    public CaptainAmericaFirstAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Throw ... — {3}, Unattach an Equipment from Captain America: He deals damage equal to that Equipment’s mana value divided as you choose among one, two, or three targets.
        Ability ability = new SimpleActivatedAbility(
                new DamageMultiEffect().setText(
                        "he deals damage equal to that Equipment's mana value divided as you choose among one, two, or three targets."),
                new GenericManaCost(3));
        ability.addCost(new CaptainAmericaFirstAvengerUnattachCost());
        ability.addTarget(new TargetAnyTargetAmount(CaptainAmericaFirstAvengerValue.instance, 1, 3));
        this.addAbility(ability.withFlavorWord("Throw ..."));

        // ... Catch — At the beginning of combat on your turn, attach up to one target Equipment you control to Captain America.
        ability = new BeginningOfCombatTriggeredAbility(
                new CaptainAmericaFirstAvengerCatchEffect()
        );
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        this.addAbility(ability.withFlavorWord("... Catch"));
    }

    private CaptainAmericaFirstAvenger(final CaptainAmericaFirstAvenger card) {
        super(card);
    }

    @Override
    public CaptainAmericaFirstAvenger copy() {
        return new CaptainAmericaFirstAvenger(this);
    }
}

enum CaptainAmericaFirstAvengerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof CaptainAmericaFirstAvengerUnattachCost && !cost.getTargets().isEmpty()) {
                Permanent equipment = game.getPermanentOrLKIBattlefield(cost.getTargets().getFirstTarget());
                if (equipment != null) {
                    amount = equipment.getManaValue();
                }
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "that Equipment's mana value";
    }
}

class CaptainAmericaFirstAvengerUnattachCost extends CostImpl implements EarlyTargetCost {

    private static final FilterPermanent filter = new FilterPermanent(SubType.EQUIPMENT, "equipment attached to this creature");

    static {
        filter.add(AttachedToSourcePredicate.instance);
    }

    CaptainAmericaFirstAvengerUnattachCost() {
        super();
    }

    protected CaptainAmericaFirstAvengerUnattachCost(final CaptainAmericaFirstAvengerUnattachCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null
                && !permanent.getAttachments().isEmpty();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || player == null) {
            return paid;
        }
        Permanent equipment = game.getPermanentOrLKIBattlefield(getTargets().getFirstTarget());
        if (equipment == null || !permanent.getAttachments().contains(equipment.getId()) ||
                !player.chooseUse(Outcome.Benefit, "Unattach " + equipment.getIdName() + "?", source, game)) {
            return false;
        }
        paid = permanent.removeAttachment(equipment.getId(), source, game);

        return paid;
    }

    @Override
    public CaptainAmericaFirstAvengerUnattachCost copy() {
        return new CaptainAmericaFirstAvengerUnattachCost(this);
    }

    @Override
    public void chooseTarget(Game game, Ability source, Player controller) {
        Target chosenEquipment = new TargetPermanent(1, 1, filter, true);
        controller.choose(Outcome.Benefit, chosenEquipment, source, game);
        addTarget(chosenEquipment);
    }

    @Override
    public String getText() {
        return "Unattach an Equipment from {this}";
    }
}

class CaptainAmericaFirstAvengerCatchEffect extends OneShotEffect {

    CaptainAmericaFirstAvengerCatchEffect() {
        super(Outcome.Benefit);
        staticText = "attach up to one target Equipment you control to {this}";
    }

    private CaptainAmericaFirstAvengerCatchEffect(final CaptainAmericaFirstAvengerCatchEffect effect) {
        super(effect);
    }

    @Override
    public CaptainAmericaFirstAvengerCatchEffect copy() {
        return new CaptainAmericaFirstAvengerCatchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Permanent creature = source.getSourcePermanentIfItStillExists(game);
        return equipment != null && creature != null && creature.addAttachment(equipment.getId(), source, game);
    }
}
