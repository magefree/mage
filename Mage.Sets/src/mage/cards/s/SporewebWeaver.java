package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.HexproofFromBlueAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SporewebWeaver extends CardImpl {

    public SporewebWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Hexproof from blue
        this.addAbility(HexproofFromBlueAbility.getInstance());

        // Whenever Sporeweb Weaver is dealt damage, you gain 1 life and create a 1/1 green Saproling creature token.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new GainLifeEffect(1), false);
        ability.addEffect(new CreateTokenEffect(new SaprolingToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private SporewebWeaver(final SporewebWeaver card) {
        super(card);
    }

    @Override
    public SporewebWeaver copy() {
        return new SporewebWeaver(this);
    }
}
