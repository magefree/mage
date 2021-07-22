
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Mournwhelk extends CardImpl {

    public Mournwhelk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Mournwhelk enters the battlefield, target player discards two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Evoke {3}{B}
        this.addAbility(new EvokeAbility("{3}{B}"));
    }

    private Mournwhelk(final Mournwhelk card) {
        super(card);
    }

    @Override
    public Mournwhelk copy() {
        return new Mournwhelk(this);
    }
}
