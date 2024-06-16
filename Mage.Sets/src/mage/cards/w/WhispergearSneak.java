package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;

/**
 *
 * @author tiera3 - based on PrizefighterConstruct and CanalDredger
 * note - draftmatters ability not implemented
 */
public final class WhispergearSneak extends CardImpl {

    public WhispergearSneak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // TODO: Draft specific abilities not implemented
        // Draft Whispergear Sneak face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft Whispergear Sneak face up - not implemented.")));

        // During the draft, you may turn Whispergear Sneak face down. If you do, look at any unopened booster pack in the draft or any booster pack not being looked at by another player.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("During the draft, you may turn Whispergear Sneak face down. If you do, "
                + "look at any unopened booster pack in the draft or any booster pack not being looked at by another player - not implemented.")));
    }

    private WhispergearSneak(final WhispergearSneak card) {
        super(card);
    }

    @Override
    public WhispergearSneak copy() {
        return new WhispergearSneak(this);
    }
}
