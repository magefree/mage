package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SinkholeSurveyor extends CardImpl {

    public SinkholeSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, you lose 1 life and this creature endures 1.
        Ability ability = new AttacksTriggeredAbility(new LoseLifeSourceControllerEffect(1));
        ability.addEffect(new EndureSourceEffect(1, "{this}").concatBy("and"));
        this.addAbility(ability);
    }

    private SinkholeSurveyor(final SinkholeSurveyor card) {
        super(card);
    }

    @Override
    public SinkholeSurveyor copy() {
        return new SinkholeSurveyor(this);
    }
}
