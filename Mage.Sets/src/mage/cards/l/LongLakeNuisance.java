package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LongLakeNuisance extends CardImpl {

    public LongLakeNuisance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, recruit.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RecruitEffect()));
    }

    private LongLakeNuisance(final LongLakeNuisance card) {
        super(card);
    }

    @Override
    public LongLakeNuisance copy() {
        return new LongLakeNuisance(this);
    }
}
