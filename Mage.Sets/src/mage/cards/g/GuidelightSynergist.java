package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuidelightSynergist extends CardImpl {

    public GuidelightSynergist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature gets +1/+0 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                ArtifactYouControlCount.instance, StaticValue.get(0), Duration.WhileOnBattlefield
        )).addHint(ArtifactYouControlHint.instance));
    }

    private GuidelightSynergist(final GuidelightSynergist card) {
        super(card);
    }

    @Override
    public GuidelightSynergist copy() {
        return new GuidelightSynergist(this);
    }
}
