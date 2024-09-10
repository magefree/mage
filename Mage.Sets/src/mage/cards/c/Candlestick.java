package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class Candlestick extends CardImpl {

    public Candlestick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.subtype.add(SubType.CLUE);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has "Whenever this creature attacks, surveil 2."
        Ability boostAbility = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));

        AttacksTriggeredAbility surveilAbility = new AttacksTriggeredAbility(new SurveilEffect(2, false));
        boostAbility.addEffect(new GainAbilityAttachedEffect(surveilAbility, AttachmentType.EQUIPMENT)
                .setText("and has \"Whenever this creature attacks, surveil 2.\" <i>(Look at the top two cards of your library, " +
                        "then put any number of them into your graveyard and the rest on top of your library in any order.)</i>"));

        this.addAbility(boostAbility);

        // {2}, Sacrifice Candlestick: Draw a card.
        this.addAbility(new ClueAbility(true));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private Candlestick(final Candlestick card) {
        super(card);
    }

    @Override
    public Candlestick copy() {
        return new Candlestick(this);
    }
}
