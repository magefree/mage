package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class SalivatingGremlins extends CardImpl {

    public SalivatingGremlins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an artifact enters the battlefield under your control, Salivating Gremlins gets +2/+0 and gains trample until end of turn.
        Effect effect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+0");
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                effect, StaticFilters.FILTER_PERMANENT_ARTIFACT, false);
        effect = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private SalivatingGremlins(final SalivatingGremlins card) {
        super(card);
    }

    @Override
    public SalivatingGremlins copy() {
        return new SalivatingGremlins(this);
    }
}
