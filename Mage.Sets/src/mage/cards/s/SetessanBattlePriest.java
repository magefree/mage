
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SetessanBattlePriest extends CardImpl {

    public SetessanBattlePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Heroic - Whenever you cast a spell that targets Setessan Battle Priest, you gain 2 life.
        this.addAbility(new HeroicAbility(new GainLifeEffect(2), false));
    }

    private SetessanBattlePriest(final SetessanBattlePriest card) {
        super(card);
    }

    @Override
    public SetessanBattlePriest copy() {
        return new SetessanBattlePriest(this);
    }
}
