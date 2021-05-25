package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
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
public final class ParanoidParishBlade extends CardImpl {

    public ParanoidParishBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Delirium</i> &mdash; Paranoid Parish-Blade gets +1/+0 and has first strike as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                DeliriumCondition.instance, "<i>Delirium</i> &mdash; {this} gets +1/+0 ");
        effect.setText("<i>Delirium</i> &mdash; {this} gets +1/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                DeliriumCondition.instance, "and has first strike as long as there are four or more card types among cards in your graveyard.");
        ability.addEffect(effect);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private ParanoidParishBlade(final ParanoidParishBlade card) {
        super(card);
    }

    @Override
    public ParanoidParishBlade copy() {
        return new ParanoidParishBlade(this);
    }
}
