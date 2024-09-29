package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Grath
 */
public final class StaffOfEdenVaultsKey extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary permanent card not named Staff of Eden, Vault's Key from a graveyard");
    private static final FilterPermanent filter2 = new FilterControlledPermanent("permanent you control but don't own");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);
    private static final Hint hint = new ValueHint("Permanents you control but don't own", xValue);

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(PermanentPredicate.instance);
        filter.add(Predicates.not(new NamePredicate("Staff of Eden, Vault's Key")));
        filter2.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public StaffOfEdenVaultsKey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // When Staff of Eden, Vault's Key enters the battlefield, put target legendary permanent card not named Staff of Eden, Vault's Key from a graveyard onto the battlefield under your control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // {T}: Draw a card for each permanent you control but don't own.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(xValue), new TapSourceCost());
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private StaffOfEdenVaultsKey(final StaffOfEdenVaultsKey card) {
        super(card);
    }

    @Override
    public StaffOfEdenVaultsKey copy() {
        return new StaffOfEdenVaultsKey(this);
    }
}
