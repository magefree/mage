
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class AshioksAdept extends CardImpl {

    public AshioksAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Ashiok's Adept, each opponent discards a card.
        this.addAbility(new HeroicAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));
    }

    private AshioksAdept(final AshioksAdept card) {
        super(card);
    }

    @Override
    public AshioksAdept copy() {
        return new AshioksAdept(this);
    }
}
