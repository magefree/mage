package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class InquisitorsOx extends CardImpl {

    public InquisitorsOx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.OX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
        this.color.setWhite(true);

        // <i>Delirium</i> &mdash; Inquisitor's Ox gets +1/+0 and has vigilance as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield), DeliriumCondition.instance, "<i>Delirium</i> &mdash; {this} gets +1/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()), DeliriumCondition.instance, "and has vigilance as long as there are four or more card types among cards in your graveyard."));
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private InquisitorsOx(final InquisitorsOx card) {
        super(card);
    }

    @Override
    public InquisitorsOx copy() {
        return new InquisitorsOx(this);
    }
}
