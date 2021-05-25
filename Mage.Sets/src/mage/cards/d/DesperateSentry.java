package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziHorrorToken;

/**
 * @author LevelX2
 */
public final class DesperateSentry extends CardImpl {

    public DesperateSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Desperate Sentry dies, create a 3/2 colorless Eldrazi Horror creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken()), false));

        // <i>Delirium</i> &mdash; Desperate Sentry gets +3/+0 as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield), DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; {this} gets +3/+0 as long as there are four or more card types among cards in your graveyard.");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private DesperateSentry(final DesperateSentry card) {
        super(card);
    }

    @Override
    public DesperateSentry copy() {
        return new DesperateSentry(this);
    }
}
