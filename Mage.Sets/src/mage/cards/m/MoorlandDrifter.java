package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MoorlandDrifter extends CardImpl {

    public MoorlandDrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Delirium</i> &mdash; Moorland Drifter has flying as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()), DeliriumCondition.instance, "<i>Delirium</i> &mdash; Moorland Drifter has flying as long as there are four or more card types among cards in your graveyard.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(CardTypesInGraveyardHint.YOU));
    }

    private MoorlandDrifter(final MoorlandDrifter card) {
        super(card);
    }

    @Override
    public MoorlandDrifter copy() {
        return new MoorlandDrifter(this);
    }
}
