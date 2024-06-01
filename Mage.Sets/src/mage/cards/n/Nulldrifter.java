package mage.cards.n;

import mage.MageInt;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Nulldrifter extends CardImpl {

    public Nulldrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When you cast this spell, draw two cards.
        this.addAbility(new CastSourceTriggeredAbility(new DrawCardSourceControllerEffect(2)));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Annihilator 1
        this.addAbility(new AnnihilatorAbility(1));

        // Evoke {2}{U}
        this.addAbility(new EvokeAbility("{2}{U}"));
    }

    private Nulldrifter(final Nulldrifter card) {
        super(card);
    }

    @Override
    public Nulldrifter copy() {
        return new Nulldrifter(this);
    }
}
