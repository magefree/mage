package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoulderbornDragon extends CardImpl {

    public BoulderbornDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever this creature attacks, surveil 1.
        this.addAbility(new AttacksTriggeredAbility(new SurveilEffect(1)));
    }

    private BoulderbornDragon(final BoulderbornDragon card) {
        super(card);
    }

    @Override
    public BoulderbornDragon copy() {
        return new BoulderbornDragon(this);
    }
}
