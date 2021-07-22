package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class ScourgeWolf extends CardImpl {

    public ScourgeWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // <i>Delirium</i> &mdash; Scourge Wolf has double strike as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                DeliriumCondition.instance, "<i>Delirium</i> &mdash; {this} has double strike as long as there are four or more card types among cards in your graveyard.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(CardTypesInGraveyardHint.YOU));
    }

    private ScourgeWolf(final ScourgeWolf card) {
        super(card);
    }

    @Override
    public ScourgeWolf copy() {
        return new ScourgeWolf(this);
    }
}
