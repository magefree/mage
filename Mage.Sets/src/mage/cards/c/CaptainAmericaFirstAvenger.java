package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.UseAttachedCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterEquipmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author Grath
 */
public final class CaptainAmericaFirstAvenger extends CardImpl {

    private static final FilterPermanent filter = new FilterEquipmentPermanent("equipment attached to this creature");
    private static final FilterPermanent subfilter = new FilterControlledPermanent("{this}");
    private static final FilterPermanent filter2 = new FilterEquipmentPermanent("equipment you control");

    static {
        subfilter.add(CaptainAmericaPredicate.instance);
        filter.add(new AttachedToPredicate(subfilter));
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public CaptainAmericaFirstAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Throw ... — {3}, Unattach an Equipment from Captain America: He deals damage equal to that Equipment’s mana value divided as you choose among one, two, or three targets.
        Ability ability = new SimpleActivatedAbility(new CaptainAmericaFirstAvengerThrowEffect(), new GenericManaCost(3));
        ability.addCost(new CaptainAmericaFirstAvengerUnattachCost());
        // 2024-10-22: It isn't in rule 601.2b yet, but you will have to choose the equipment before the actual targets to comply with rules 601.2c/601.2d
        // This is technically happening in step 601.2c, but it's still in time for the following TargetAnyTargetAmount to work properly.
        ability.addTarget(new TargetPermanent(1, 1, filter, true));
        ability.addTarget(new TargetAnyTargetAmount(CaptainAmericaFirstAvengerValue.instance, 3));
        this.addAbility(ability.withFlavorWord("Throw ..."));

        // ... Catch — At the beginning of combat on your turn, attach up to one target Equipment you control to Captain America.
        ability = new BeginningOfCombatTriggeredAbility(
                new CaptainAmericaFirstAvengerCatchEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter2));
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

enum CaptainAmericaPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        if (!input.getObject().getId().equals(input.getSourceId())) {
            return false;
        }
        int zcc = input.getSource().getSourceObjectZoneChangeCounter();
        return zcc == input.getObject().getZoneChangeCounter(game);
    }

    @Override
    public String toString() {
        return "Another";
    }
}

enum CaptainAmericaFirstAvengerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        UUID chosenEquipment = sourceAbility.getFirstTarget();
        if (chosenEquipment != null) {
            Permanent equipment = game.getPermanentOrLKIBattlefield(chosenEquipment);
            if (equipment != null) {
                amount = equipment.getManaValue();
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

class CaptainAmericaFirstAvengerUnattachCost extends UseAttachedCost {

    public CaptainAmericaFirstAvengerUnattachCost() {
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
        if (permanent == null) {
            return paid;
        }
        Permanent equipment = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (!permanent.getAttachments().contains(source.getFirstTarget()) ||
                !player.chooseUse(Outcome.Benefit, "Unattach " + equipment.getName() + "?", source, game)) {
            return false;
        }
        paid = permanent.removeAttachment(source.getFirstTarget(), source, game);

        return paid;
    }

    @Override
    public CaptainAmericaFirstAvengerUnattachCost copy() {
        return new CaptainAmericaFirstAvengerUnattachCost(this);
    }

    @Override
    public String getText() {
        return "Unattach an Equipment from " + this.name;
    }
}

class CaptainAmericaFirstAvengerThrowEffect extends DamageMultiEffect {
    CaptainAmericaFirstAvengerThrowEffect() {
        super(CaptainAmericaFirstAvengerValue.instance, "he");
        staticText = "he deals damage equal to that Equipment's mana value divided as you choose among one, two, or three targets.";
    }

    private CaptainAmericaFirstAvengerThrowEffect(final CaptainAmericaFirstAvengerThrowEffect effect) {
        super(effect);
    }

    @Override
    public CaptainAmericaFirstAvengerThrowEffect copy() {
        return new CaptainAmericaFirstAvengerThrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // The first target is the no-longer-needed choice of equipment attached to Captain America.
        // The second target is the TargetAnyTargetAmount which DamageMultEffect uses to assign the proper damage.
        source.getTargets().remove(0);
        return super.apply(game, source);
    }
}

class CaptainAmericaFirstAvengerCatchEffect extends OneShotEffect {

    CaptainAmericaFirstAvengerCatchEffect() {
        super(Outcome.Benefit);
        staticText = "attach target Equipment you control to {this}";
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