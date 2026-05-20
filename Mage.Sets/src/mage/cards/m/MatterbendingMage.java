package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MatterbendingMage extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    public MatterbendingMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, return up to one other target creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // Whenever you cast a spell with {X} in its mana cost, this creature can't be blocked this turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CantBeBlockedSourceEffect(Duration.EndOfTurn), filter, false
        ));
    }

    private MatterbendingMage(final MatterbendingMage card) {
        super(card);
    }

    @Override
    public MatterbendingMage copy() {
        return new MatterbendingMage(this);
    }
}
