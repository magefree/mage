package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class MoldgrafScavenger extends CardImpl {

    public MoldgrafScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // <i>Delirium</i> &mdash; Moldgraf Scavenger gets +3/+0 as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; {this} gets +3/+0 as long as there are four or more card types among cards in your graveyard"))
                .addHint(CardTypesInGraveyardHint.YOU));
    }

    private MoldgrafScavenger(final MoldgrafScavenger card) {
        super(card);
    }

    @Override
    public MoldgrafScavenger copy() {
        return new MoldgrafScavenger(this);
    }
}
