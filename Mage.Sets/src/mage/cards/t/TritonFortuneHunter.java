
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TritonFortuneHunter extends CardImpl {

    public TritonFortuneHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Triton Fortune Hunter, draw a card.
        this.addAbility(new HeroicAbility(new DrawCardSourceControllerEffect(1)));
    }

    private TritonFortuneHunter(final TritonFortuneHunter card) {
        super(card);
    }

    @Override
    public TritonFortuneHunter copy() {
        return new TritonFortuneHunter(this);
    }
}
