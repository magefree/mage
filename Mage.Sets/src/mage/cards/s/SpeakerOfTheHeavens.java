package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MoreThanStartingLifeTotalCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.permanent.token.AngelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpeakerOfTheHeavens extends CardImpl {

    public SpeakerOfTheHeavens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {T}: Create a 4/4 white Angel creature token with flying. Activate only if you have at least 7 more life than your starting life total and only as a sorcery.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new AngelToken()),
                new TapSourceCost(), MoreThanStartingLifeTotalCondition.SEVEN,
                TimingRule.SORCERY
        ));
    }

    private SpeakerOfTheHeavens(final SpeakerOfTheHeavens card) {
        super(card);
    }

    @Override
    public SpeakerOfTheHeavens copy() {
        return new SpeakerOfTheHeavens(this);
    }
}