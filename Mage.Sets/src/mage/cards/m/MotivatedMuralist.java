package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Codex
 */
public final class MotivatedMuralist extends CardImpl {

    public MotivatedMuralist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // When this creature enters, conjure a card named Inspiring Statuary into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConjureCardEffect("Inspiring Statuary")));
    }

    private MotivatedMuralist(final MotivatedMuralist card) {
        super(card);
    }

    @Override
    public MotivatedMuralist copy() {
        return new MotivatedMuralist(this);
    }
}
