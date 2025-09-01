package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicObserver extends CardImpl {

    public AngelicObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Affinity for Citizens
        this.addAbility(new AffinityAbility(AffinityType.CITIZENS));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private AngelicObserver(final AngelicObserver card) {
        super(card);
    }

    @Override
    public AngelicObserver copy() {
        return new AngelicObserver(this);
    }
}
