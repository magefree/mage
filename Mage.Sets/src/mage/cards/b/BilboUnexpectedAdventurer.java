package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author muz
 */
public final class BilboUnexpectedAdventurer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");
    private static final FilterCard filterCard = new FilterNonlandCard("nonland permanent card with mana value 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 3));
        filterCard.add(PermanentPredicate.instance);
        filterCard.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public BilboUnexpectedAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bilbo can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Bilbo deals combat damage to a player or battle, put up to one target nonland permanent card with mana value 3 or less from a graveyard onto the battlefield under its owner's control.
        Ability ability = new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(
            new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false)
                .setText("put up to one target nonland permanent card with mana value 3 or less from a graveyard onto the battlefield under its owner's control"),
            false
        );
        ability.addTarget(new TargetCardInGraveyard(0, 1, filterCard));
        this.addAbility(ability);
    }

    private BilboUnexpectedAdventurer(final BilboUnexpectedAdventurer card) {
        super(card);
    }

    @Override
    public BilboUnexpectedAdventurer copy() {
        return new BilboUnexpectedAdventurer(this);
    }
}
