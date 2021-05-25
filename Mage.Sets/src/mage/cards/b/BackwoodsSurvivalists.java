package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class BackwoodsSurvivalists extends CardImpl {

    public BackwoodsSurvivalists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // <i>Delirium</i> &mdash; Backwoods Survivalists gets +1/+1 and has trample as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), DeliriumCondition.instance, "<i>Delirium</i> &mdash; {this} gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()), DeliriumCondition.instance, "and has trample as long as there are four or more card types among cards in your graveyard."));
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private BackwoodsSurvivalists(final BackwoodsSurvivalists card) {
        super(card);
    }

    @Override
    public BackwoodsSurvivalists copy() {
        return new BackwoodsSurvivalists(this);
    }
}
