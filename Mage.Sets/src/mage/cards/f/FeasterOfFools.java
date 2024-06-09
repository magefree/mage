package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeasterOfFools extends CardImpl {

    public FeasterOfFools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Devour 2
        this.addAbility(new DevourAbility(2));
    }

    private FeasterOfFools(final FeasterOfFools card) {
        super(card);
    }

    @Override
    public FeasterOfFools copy() {
        return new FeasterOfFools(this);
    }
}
