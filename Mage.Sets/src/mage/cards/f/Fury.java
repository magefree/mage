package mage.cards.f;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Fury extends CardImpl {

    private static final FilterCard filter = new FilterCard("a red card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public Fury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Fury enters the battlefield, it deals 4 damage divided as you choose among any number of target creatures and/or planeswalkers.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageMultiEffect(4, "it")
        );
        ability.addTarget(new TargetCreatureOrPlaneswalkerAmount(4));
        this.addAbility(ability);

        // Evoke—Exile a red card from your hand.
        this.addAbility(new EvokeAbility(new ExileFromHandCost(new TargetCardInHand(filter))));
    }

    private Fury(final Fury card) {
        super(card);
    }

    @Override
    public Fury copy() {
        return new Fury(this);
    }
}
